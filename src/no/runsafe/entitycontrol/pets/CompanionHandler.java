package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.World;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.event.player.IPlayerQuitEvent;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerChangedWorldEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerQuitEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.tools.nms.EntityRegister;
import no.runsafe.framework.minecraft.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CompanionHandler implements IServerReady, IPlayerRightClick, IPlayerChangedWorldEvent, IPlayerQuitEvent, IPlayerInteractEntityEvent
{
	/**
	 * Constructor for CompanionHandler.
	 * @param console The console.
	 * @param server The server.
	 */
	public CompanionHandler(IConsole console, IServer server, IScheduler scheduler)
	{
		this.console = console;
		CompanionHandler.server = server;
		this.scheduler = scheduler;
	}

	/**
	 * Attempt to spawn a companion.
	 * @param location Companion spawn point.
	 * @param type Companion type to spawn.
	 * @param follower Player the companion will follow.
	 */
	public void spawnCompanion(ILocation location, CompanionType type, IPlayer follower)
	{
		IWorld world = location.getWorld();
		World rawWorld = ObjectUnwrapper.getMinecraft(location.getWorld());

		if (rawWorld == null)
			return;

		try
		{
			ICompanionPet pet = (ICompanionPet) type.getEntityClass().getConstructor(IWorld.class).newInstance(world);
			pet.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
			pet.setFollowingPlayer(follower);
			rawWorld.addEntity((EntityInsentient) pet);

			UUID playerUUID = follower.getUniqueId();

			if (!summonedPets.containsKey(playerUUID))
				summonedPets.put(playerUUID, new ArrayList<SummonedPet>(1));

			summonedPets.get(playerUUID).add(new SummonedPet(type, ((EntityInsentient) pet).getId()));
		}
		catch (Exception e)
		{
			console.logException(e);
		}
	}

	/**
	 * Detect when a player tries to summon/remove a companion.
	 * @param player Companion owner.
	 * @param usingItem Item the player is holding.
	 * @param targetBlock Targeted block. Influences nothing.
	 * @return True when player isn't holding a companion egg.
	 */
	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, IBlock targetBlock)
	{
		//Check if player is holding a monster egg
		if (usingItem == null || !usingItem.is(Item.Miscellaneous.MonsterEgg.Any))
			return true;

		//Check if monster egg has lore set
		List<String> lore = usingItem.getLore();
		if (lore == null || lore.isEmpty())
			return true;

		String petString = null;
		for (String loreString : lore)
		{
			if (!loreString.startsWith("§7Pet: "))
				continue;

			petString = loreString;
			break;
		}

		if (petString == null)
			return false;

		//Get the companion's type
		CompanionType type = null;
		for (CompanionType companionType : CompanionType.values())
		{
			if (!petString.endsWith(companionType.getTitle()))
				continue;

			type = companionType;
			break;
		}

		//Summon or remove companion
		if (type == null)
			return false;
		SummonedPet summonedPet = getPlayerSummoned(player, type);

		if (summonedPet == null)
			spawnCompanion(player.getLocation(), type, player);
		else
			removeSummonedPet(player, summonedPet);

		return false;
	}

	/**
	 * Gets companion pet the player has summoned if they have summoned one.
	 * @param player Companion pet owner.
	 * @param type Companion pet type.
	 * @return Companion pet. Returns null if not summoned yet.
	 */
	public SummonedPet getPlayerSummoned(IPlayer player, CompanionType type)
	{
		UUID playerUUID = player.getUniqueId();
		if (!summonedPets.containsKey(playerUUID))
			return null;

		for (SummonedPet summonedPet : summonedPets.get(playerUUID))
			if (summonedPet.getType() == type)
				return summonedPet;

		return null;
	}

	/**
	 * Find whether or not an entity is summoned.
	 * @param entity Entity to find if summoned.
	 * @return True if entity is summoned, otherwise false.
	 */
	public static boolean entityIsSummoned(EntityInsentient entity)
	{
		for (Map.Entry<UUID, List<SummonedPet>> node : summonedPets.entrySet())
			for (SummonedPet pet : node.getValue())
				if (entity.getId() == pet.getEntityID())
					return true;

		return false;
	}

	/**
	 * Removes a player's pet.
	 * @param player Pet owner.
	 * @param pet Object do delete.
	 */
	public void removeSummonedPet(IPlayer player, SummonedPet pet)
	{
		summonedPets.get(player.getUniqueId()).remove(pet);
	}

	/**
	 * Triggered when player changes world.
	 * Kill player's companion pets.
	 * @param event Event that happens when a player changes worlds.
	 */
	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		summonedPets.remove(event.getPlayer().getUniqueId());
	}

	/**
	 * Kill player's companion pets when they log out.
	 * @param event Player log out event.
	 */
	@Override
	public void OnPlayerQuit(RunsafePlayerQuitEvent event)
	{
		summonedPets.remove(event.getPlayer().getUniqueId());
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		RunsafeEntity runsafePet = event.getRightClicked();
		if (interactTimer.containsKey(runsafePet))
		{
			event.cancel();
			return;
		}

		// Check if right clicked entity is a companion pet.
		if (!(((CraftEntity) runsafePet.getRaw()).getHandle() instanceof ICompanionPet))
			return;
		ICompanionPet pet = (ICompanionPet) ((CraftEntity) runsafePet.getRaw()).getHandle();

		// Play the companion's idle sound.
		Sound interactSound = pet.getInteractSound();
		if (interactSound != null)
		{
			interactSound.Play(runsafePet.getLocation(), 1.0F, 1.5F);
			interactTimer.put(runsafePet, scheduler.startSyncTask(() ->
			{
				if (interactTimer.containsKey(runsafePet))
					interactTimer.remove(runsafePet);
			}, 2));
		}

		event.cancel();
	}

	@Override
	public void OnServerReady()
	{
		for (CompanionType type : CompanionType.values())
			EntityRegister.registerEntity(type.getEntityClass(), "Companion" + type.getName(), type.getId());
	}

	private final IScheduler scheduler;
	private final IConsole console;
	public static IServer server;
	private static final ConcurrentHashMap<IEntity, Integer> interactTimer = new ConcurrentHashMap<>();
	public static ConcurrentHashMap<UUID, List<SummonedPet>> summonedPets = new ConcurrentHashMap<UUID, List<SummonedPet>>(0);
	//public static ConcurrentHashMap<String, List<CompanionType>> summonedPets = new ConcurrentHashMap<String, List<CompanionType>>(0);
	//public static ConcurrentHashMap<String, List<Integer>> summonedPetIds = new ConcurrentHashMap<String, List<Integer>>(0);
}
