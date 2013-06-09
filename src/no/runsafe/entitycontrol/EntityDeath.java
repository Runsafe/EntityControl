package no.runsafe.entitycontrol;

import no.runsafe.framework.event.entity.IEntityDeathEvent;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.server.entity.LivingEntity;
import no.runsafe.framework.server.entity.RunsafeEntity;
import no.runsafe.framework.server.event.entity.RunsafeEntityDeathEvent;
import no.runsafe.framework.server.item.RunsafeItemStack;
import org.bukkit.Material;

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
