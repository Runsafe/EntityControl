package no.runsafe.entitycontrol;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.player.IPlayerTeleport;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.LivingEntity;

public class MountedTeleportHandler implements IPlayerTeleport
{
	public MountedTeleportHandler(IScheduler scheduler)
	{
		this.scheduler = scheduler;
	}

	@Override
	public boolean OnPlayerTeleport(final IPlayer player, ILocation from, ILocation to)
	{
		if (player.isInsideVehicle() && from.getWorld().isWorld(to.getWorld()))
		{
			final IEntity vehicle = player.getVehicle();
			if (vehicle.getEntityType() == LivingEntity.Horse)
			{
				player.eject();
				scheduler.startSyncTask(new Runnable()
				{
					@Override
					public void run()
					{
						vehicle.teleport(player);
						vehicle.setPassenger(player);
					}
				}, 10L);
			}
		}
		return true;
	}

	private final IScheduler scheduler;
}
