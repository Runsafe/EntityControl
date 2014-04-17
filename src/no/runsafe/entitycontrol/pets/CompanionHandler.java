package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityInsentient;
import net.minecraft.server.v1_7_R2.World;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IServer;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.tools.nms.EntityRegister;

public class CompanionHandler implements IServerReady
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
			}
			catch (Exception e)
			{
				console.logException(e);
			}
		}
	}

	@Override
	public void OnServerReady()
	{
		for (CompanionType type : CompanionType.values())
			EntityRegister.registerEntity(type.getEntityClass(), "Companion" + type.getName(), type.getId());
	}

	private final IConsole console;
	public static IServer server;
}
