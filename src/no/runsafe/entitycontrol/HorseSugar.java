package no.runsafe.entitycontrol;

import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Buff;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class HorseSugar implements IPlayerInteractEntityEvent
{
	public HorseSugar()
	{
		speedBuff = Buff.Utility.Movement.IncreaseSpeed.ambient(true).amplification(1).duration(900);
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		IPlayer player = event.getPlayer();
		RunsafeMeta item = player.getItemInHand();
		if (item == null || !item.is(Item.Materials.Sugarcane))
			return;

		RunsafeEntity entity = event.getRightClicked();
		if (entity.getEntityType() == LivingEntity.Horse)
		{
			speedBuff.applyTo((ILivingEntity) entity);
			player.removeExactItem(item, 1);
			event.cancel();
		}
	}

	private final Buff speedBuff;
}
