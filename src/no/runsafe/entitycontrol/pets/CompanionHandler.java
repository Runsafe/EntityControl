package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.World;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.block.IBlock;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.event.player.IPlayerChangedWorldEvent;
import no.runsafe.framework.api.event.player.IPlayerRightClick;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerChangedWorldEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.tools.nms.EntityRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CompanionHandler implements IServerReady, IPlayerRightClick, IPlayerChangedWorldEvent
{
	/**
	 * Constructor for CompanionHandler.
	 * @param console The console.
	 * @param server The server.
	 */
	public CompanionHandler(IConsole console, IServer server)
	{
		this.console = console;
		CompanionHandler.server = server;
	}

	/**
	 * Attempt to spawn a companion.
	 * @param location Location to spawn companion.
	 * @param type Companion type to spawn.
	 * @param follower Player the companion will follow.
	 */
	public void spawnCompanion(ILocation location, CompanionType type, IPlayer follower)
	{
		World world = ObjectUnwrapper.getMinecraft(location.getWorld());

		if (world == null)
			return;

		try
		{
			ICompanionPet pet = (ICompanionPet) type.getEntityClass().getConstructor(World.class).newInstance(world);
			pet.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
			pet.setFollowingPlayer(follower);
			world.addEntity((EntityInsentient) pet);

			String playerName = follower.getName();

			if (!summonedPets.containsKey(playerName))
				summonedPets.put(playerName, new ArrayList<SummonedPet>(1));

			summonedPets.get(playerName).add(new SummonedPet(type, ((EntityInsentient) pet).getId()));
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
		if (usingItem != null && usingItem.is(Item.Miscellaneous.MonsterEgg.Any))
		{
			//Check if monster egg has lore set
			List<String> lore = usingItem.getLore();
			if (lore != null && !lore.isEmpty())
			{
				String petString = null;
				for (String loreString : lore)
				{
					if (loreString.startsWith("§7Pet: "))
					{
						petString = loreString;
						break;
					}
				}

				if (petString == null)
					return false;

				//Get the companion's type
				CompanionType type = null;
				for (CompanionType companionType : CompanionType.values())
				{
					if (petString.endsWith(companionType.getTitle()))
					{
						type = companionType;
						break;
					}
				}

				//Summon or remove companion
				if (type != null)
				{
					SummonedPet summonedPet = getPlayerSummoned(player, type);

					if (summonedPet == null)
						spawnCompanion(player.getLocation(), type, player);
					else
						removeSummonedPet(player, summonedPet);
				}

				return false;
			}
		}
		return true;
	}

	/**
	 * Gets companion pet the player has summoned if they have summoned one.
	 * @param player Companion pet owner.
	 * @param type Companion pet type.
	 * @return Companion pet. Returns null if not summoned yet.
	 */
	public SummonedPet getPlayerSummoned(IPlayer player, CompanionType type)
	{
		String playerName = player.getName();
		if (!summonedPets.containsKey(playerName))
			return null;

		for (SummonedPet summonedPet : summonedPets.get(playerName))
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
		for (Map.Entry<String, List<SummonedPet>> node : summonedPets.entrySet())
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
		summonedPets.get(player.getName()).remove(pet);
	}

	/**
	 * Triggered when player changes world.
	 * Kill player's companion pets.
	 * @param event Event that happens when a player changes worlds.
	 */
	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		summonedPets.remove(event.getPlayer().getName());
	}

	@Override
	public void OnServerReady()
	{
		for (CompanionType type : CompanionType.values())
			EntityRegister.registerEntity(type.getEntityClass(), "Companion" + type.getName(), type.getId());
	}

	private final IConsole console;
	public static IServer server;
	public static ConcurrentHashMap<String, List<SummonedPet>> summonedPets = new ConcurrentHashMap<String, List<SummonedPet>>(0);
	//public static ConcurrentHashMap<String, List<CompanionType>> summonedPets = new ConcurrentHashMap<String, List<CompanionType>>(0);
	//public static ConcurrentHashMap<String, List<Integer>> summonedPetIds = new ConcurrentHashMap<String, List<Integer>>(0);
}
