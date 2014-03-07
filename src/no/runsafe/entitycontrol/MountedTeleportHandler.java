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
		if (to.getWorld().isWorld(from.getWorld()) && to.distance(from) > 500)
		{
			IEntity vehicle = player.getVehicle();
			if (vehicle != null && vehicle.getEntityType() == LivingEntity.Horse)
			{
				vehicle.eject();
				vehicle.teleport(to);
				//player.teleport(to);
				return false;
			}
		}
		return true;
	}
}
