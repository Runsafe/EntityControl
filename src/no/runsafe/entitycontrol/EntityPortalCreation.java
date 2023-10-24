package no.runsafe.entitycontrol;

import no.runsafe.framework.api.event.entity.IEntityCreatePortalEvent;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityCreatePortalEvent;

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
			event.cancel();
	}

	private final Options options;
}
