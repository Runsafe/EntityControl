package no.runsafe.entitycontrol;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.player.IPlayerTeleport;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.LivingEntity;

public class MountedTeleportHandler implements IPlayerTeleport
{
	@Override
	public boolean OnPlayerTeleport(IPlayer player, ILocation from, ILocation to)
	{
		player.sendColouredMessage("Event detected");
		if (to.getWorld().isWorld(from.getWorld()) && to.distance(from) > 500)
		{
			player.sendColouredMessage("Within world and over 500 blocks away.");
			IEntity vehicle = player.getVehicle();
			if (vehicle != null && vehicle.getEntityType() == LivingEntity.Horse)
			{
				player.sendColouredMessage("Vehicle is horse!");
				vehicle.eject();
				vehicle.teleport(to);
				//player.teleport(to);
				return false;
			}
		}
		return true;
	}
}
