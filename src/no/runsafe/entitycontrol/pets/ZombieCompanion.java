package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.ItemStack;
import net.minecraft.server.v1_7_R2.Items;
import net.minecraft.server.v1_7_R2.World;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeSkull;

public class ZombieCompanion extends CompanionPetHumanoid
{
	public ZombieCompanion(World world)
	{
		super(world);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.zombie.say");
		return false;
	}

	@Override
	protected void randomThing()
	{
		super.randomThing();
		float ran = random.nextFloat();

		if (ran == 0.2)
		{
			setEquipment(0, new ItemStack(Items.IRON_SWORD));
		}
		else if (ran == 0.3)
		{
			setEquipment(0, new ItemStack(Items.APPLE));
		}
		else if (ran == 0.4)
		{
			setEquipment(0, new ItemStack(Items.STICK));
		}
		else if (ran == 0.5)
		{
			setEquipment(0, new ItemStack(Items.CARROT_STICK));
		}
		else if (ran == 0.6)
		{
			setEquipment(0, new ItemStack(Items.FISHING_ROD));
		}
		else if (ran == 0.7)
		{
			RunsafeSkull item = (RunsafeSkull) Item.Decoration.Head.Human.getItem();
			item.setOwner(player.displayName);
			setEquipment(0, ObjectUnwrapper.getMinecraft(item));
		}
	}
}
