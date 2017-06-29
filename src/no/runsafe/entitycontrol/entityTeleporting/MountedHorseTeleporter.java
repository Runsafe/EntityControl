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
		if (!from.getWorld().isWorld(to.getWorld()) || from.distance(to) < 200)
			return true;

		IWorld world = from.getWorld();
		for (IEntity entity : world.getEntities())
		{
			if (!(entity instanceof ILivingEntity))
				continue;

			ILivingEntity livingEntity = (ILivingEntity) entity;
			if (!livingEntity.isLeashed() || !(livingEntity.getLeashHolder() instanceof IPlayer))
				continue;

			IPlayer leashHolder = (IPlayer) livingEntity.getLeashHolder();
			if (!leashHolder.equals(player))
				continue;

			final Class<?> entityClass = ObjectUnwrapper.getMinecraft(livingEntity).getClass();
			final String entityData = EntityCompacter.convertEntityToString(livingEntity);
			livingEntity.remove();

			scheduler.startSyncTask(() -> EntityCompacter.spawnEntityFromString(entityClass, to, entityData), 10L);
		}
		return true;
	}

	private final IScheduler scheduler;
}
