package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.World;

public class VillagerCompanion extends CompanionPetAnimal
{
	public VillagerCompanion(World world)
	{
		super(world);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.villager.idle");
		return false;
	}
}
