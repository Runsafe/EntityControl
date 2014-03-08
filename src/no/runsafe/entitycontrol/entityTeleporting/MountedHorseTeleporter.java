package no.runsafe.entitycontrol.entityTeleporting;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IScheduler;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.event.player.IPlayerTeleport;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.tools.EntityCompacter;

public class MountedHorseTeleporter implements IPlayerTeleport
{
	public MountedHorseTeleporter(IScheduler scheduler)
	{
		this.scheduler = scheduler;
	}

	@Override
	public boolean OnPlayerTeleport(IPlayer player, ILocation from, final ILocation to)
	{
		IWorld world = from.getWorld();
		for (IEntity entity : world.getEntities())
		{
			if (entity instanceof ILivingEntity)
			{
				ILivingEntity livingEntity = (ILivingEntity) entity;
				if (livingEntity.isLeashed() && livingEntity.getLeashHolder() instanceof IPlayer)
				{
					IPlayer leashHolder = (IPlayer) livingEntity.getLeashHolder();
					if (leashHolder.getName().equals(player.getName()))
					{
						final Class<?> entityClass = ObjectUnwrapper.getMinecraft(livingEntity).getClass();
						final String entityData = EntityCompacter.convertEntityToString(livingEntity);
						livingEntity.remove();

						scheduler.startSyncTask(new Runnable()
						{
							@Override
							public void run()
							{
								EntityCompacter.spawnEntityFromString(entityClass, to, entityData);
							}
						}, 10L);
					}
				}
			}
		}
		return true;
	}

	private final IScheduler scheduler;
}
