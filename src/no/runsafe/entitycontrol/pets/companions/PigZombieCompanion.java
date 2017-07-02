package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import no.runsafe.entitycontrol.pets.CompanionPetHumanoid;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.Sound;
import no.runsafe.framework.minecraft.item.meta.RunsafeSkull;

public class PigZombieCompanion extends CompanionPetHumanoid
{
	public PigZombieCompanion(IWorld world)
	{
		super(world);
	}

	@Override
	public Sound getInteractSound()
	{
		return Sound.Creature.PigZombie.Idle;
	}

	@Override
	protected void randomThing()
	{
		super.randomThing();
		float ran = random.nextFloat();

		if (ran == 0.2)
		{
			setEquipment(0, new ItemStack(Items.POISONOUS_POTATO));
		}
		else if (ran == 0.3)
		{
			setEquipment(0, new ItemStack(Items.FIRE_CHARGE));
		}
		else if (ran == 0.4)
		{
			setEquipment(0, new ItemStack(Items.GOLDEN_SWORD));
		}
		else if (ran == 0.5)
		{
			setEquipment(0, new ItemStack(Items.BONE));
		}
		else if (ran == 0.6)
		{
			setEquipment(0, new ItemStack(Items.BLAZE_ROD));
		}
		else if (ran == 0.7)
		{
			RunsafeSkull item = (RunsafeSkull) Item.Decoration.Head.Human.getItem();
			item.setOwner(player.getName());
			setEquipment(0, ObjectUnwrapper.getMinecraft(item));
		}
	}
}
