package no.runsafe.entitycontrol;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.event.player.IPlayerTeleport;
import no.runsafe.framework.api.player.IPlayer;

public class MountedTeleportHandler implements IPlayerTeleport
{
	@Override
	public boolean OnPlayerTeleport(IPlayer player, ILocation from, ILocation to)
	{
		if (to.getWorld().isWorld(from.getWorld()))
		{
			IWorld world = player.getWorld();
			if (world == null)
				return true;

			boolean hasLoaded = false;

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
							if (!hasLoaded)
							{
								if (to.getChunk().isUnloaded())
									to.getChunk().load();

								hasLoaded = true;
							}

							livingEntity.setLeashHolder(null);
							entity.teleport(to);
						}
					}
				}
			}

		}
		return true;
	}
}
