package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.World;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.tools.nms.EntityRegister;

public class CompanionHandler implements IServerReady
{
	public void spawnCompanion(ILocation location)
	{
		World world = ObjectUnwrapper.getMinecraft(location.getWorld());

		if (world != null)
		{
			CompanionPet pet = new CompanionPet(world);
			pet.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
			world.addEntity(pet);
			pet.setBaby(true);
		}
	}

	@Override
	public void OnServerReady()
	{
		EntityRegister.registerEntity(CompanionPet.class, "CompanionPet", 54);
	}
}
