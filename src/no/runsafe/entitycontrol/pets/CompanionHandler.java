package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.World;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.tools.nms.EntityRegister;

public class CompanionHandler implements IServerReady
{
	public CompanionHandler(IConsole console)
	{
		this.console = console;
	}

	public void spawnCompanion(ILocation location, CompanionType type)
	{
		World world = ObjectUnwrapper.getMinecraft(location.getWorld());

		if (world != null)
		{
			try
			{
				CompanionPet pet = (CompanionPet) type.getEntityClass().newInstance();
				pet.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
				world.addEntity(pet);
				pet.setBaby(true);
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
}
