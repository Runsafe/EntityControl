package no.runsafe.entitycontrol;

import no.runsafe.framework.event.entity.IEntityCreatePortalEvent;
import no.runsafe.framework.server.entity.LivingEntity;
import no.runsafe.framework.server.event.entity.RunsafeEntityCreatePortalEvent;

public class EntityPortalCreation implements IEntityCreatePortalEvent
{
	public EntityPortalCreation(Options options)
	{
		this.options = options;
	}

	@Override
	public void OnEntityCreatePortal(RunsafeEntityCreatePortalEvent event)
	{
		if (event.getEntity().getEntityType() == LivingEntity.EnderDragon && this.options.disableEnderPortalCreation())
			event.setCancelled(true);
	}

	private Options options;
}
