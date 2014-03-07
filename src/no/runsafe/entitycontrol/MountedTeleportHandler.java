package no.runsafe.entitycontrol;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.player.IPlayerTeleportEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerTeleportEvent;

public class MountedTeleportHandler implements IPlayerTeleportEvent
{
	@Override
	public void OnPlayerTeleport(RunsafePlayerTeleportEvent event)
	{
		IPlayer player = event.getPlayer();
		ILocation to = event.getTo();
		ILocation from = event.getFrom();

		if (player.isInsideVehicle() && to != null && from != null && to.getWorld().isWorld(from.getWorld()) && to.distance(from) > 500)
		{
			IEntity vehicle = player.getVehicle();
			if (vehicle != null && vehicle.getEntityType() == LivingEntity.Horse)
			{
				vehicle.eject();
				vehicle.teleport(to);
				player.teleport(to);
				event.cancel();
			}
		}
	}
}
