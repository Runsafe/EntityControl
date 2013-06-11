package no.runsafe.entitycontrol;

import no.runsafe.framework.api.event.entity.IEntityDeathEvent;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.entity.RunsafeEntityDeathEvent;

public class EntityDeath implements IEntityDeathEvent
{
	public EntityDeath(Options options)
	{
		this.options = options;
	}

	@Override
	public void OnEntityDeath(RunsafeEntityDeathEvent event)
	{
		RunsafeEntity entity = event.getEntity();
		if (entity.getEntityType() == LivingEntity.EnderDragon && this.options.enderDragonDropsEgg())
			Item.Special.DragonEgg.Drop(entity.getLocation());
	}

	private Options options;
}
