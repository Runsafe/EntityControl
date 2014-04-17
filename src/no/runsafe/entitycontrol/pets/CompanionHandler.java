package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityInsentient;
import net.minecraft.server.v1_7_R2.World;
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

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CompanionHandler implements IServerReady, IPlayerRightClick, IPlayerChangedWorldEvent
{
	public CompanionHandler(IConsole console, IServer server)
	{
		this.console = console;
		CompanionHandler.server = server;
	}

	public void spawnCompanion(ILocation location, CompanionType type, IPlayer follower)
	{
		World world = ObjectUnwrapper.getMinecraft(location.getWorld());

		if (world != null)
		{
			try
			{
				ICompanionPet pet = (ICompanionPet) type.getEntityClass().getConstructor(World.class).newInstance(world);
				pet.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
				pet.setFollowingPlayer(follower);
				world.addEntity((EntityInsentient) pet);

				String playerName = follower.getName();
				summonedPets.put(playerName, type);
				summonedPetIds.put(playerName, ((EntityInsentient) pet).getId());
			}
			catch (Exception e)
			{
				console.logException(e);
			}
		}
	}

	@Override
	public boolean OnPlayerRightClick(IPlayer player, RunsafeMeta usingItem, IBlock targetBlock)
	{
		if (usingItem != null && usingItem.is(Item.Miscellaneous.MonsterEgg.Any))
		{
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

				CompanionType type = null;
				for (CompanionType companionType : CompanionType.values())
				{
					if (petString.endsWith(companionType.getTitle()))
					{
						type = companionType;
						break;
					}
				}

				if (type != null)
				{
					String playerName = player.getName();
					if (summonedPets.containsKey(playerName))
					{
						if (summonedPets.get(playerName) == type)
							untrackPlayer(player);
						else
							spawnCompanion(player.getLocation(), type, player);
					}
					else
					{
						spawnCompanion(player.getLocation(), type, player);
					}
				}

				return false;
			}
		}
		return true;
	}

	@Override
	public void OnPlayerChangedWorld(RunsafePlayerChangedWorldEvent event)
	{
		untrackPlayer(event.getPlayer());
	}

	private void untrackPlayer(IPlayer player)
	{
		String playerName = player.getName();

		summonedPets.remove(playerName);
		summonedPetIds.remove(playerName);
	}

	@Override
	public void OnServerReady()
	{
		for (CompanionType type : CompanionType.values())
			EntityRegister.registerEntity(type.getEntityClass(), "Companion" + type.getName(), type.getId());
	}

	private final IConsole console;
	public static IServer server;
	public static ConcurrentHashMap<String, CompanionType> summonedPets = new ConcurrentHashMap<String, CompanionType>(0);
	public static ConcurrentHashMap<String, Integer> summonedPetIds = new ConcurrentHashMap<String, Integer>(0);
}
