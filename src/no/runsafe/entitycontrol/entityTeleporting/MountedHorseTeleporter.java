package no.runsafe.entitycontrol.entityTeleporting;

import net.minecraft.server.v1_7_R1.EntityHorse;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.event.player.IPlayerTeleport;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.tools.EntityCompacter;

public class MountedHorseTeleporter implements IPlayerTeleport
{
	@Override
	public boolean OnPlayerTeleport(IPlayer player, ILocation from, ILocation to)
	{
		if (from.getWorld().isWorld(to.getWorld()) && from.distance(to) > 500)
		{
			IEntity vehicle = player.getVehicle();
			if (vehicle != null && vehicle.getEntityType() == LivingEntity.Horse)
			{
				String data = EntityCompacter.convertEntityToString((ILivingEntity) vehicle);
				vehicle.remove();
				EntityCompacter.spawnEntityFromString(EntityHorse.class, to, data);
			}
		}
		return true;
	}
}
