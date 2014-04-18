package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.World;

public class BlacksmithCompanion extends CompanionPetVillager
{
	public BlacksmithCompanion(World world)
	{
		super(world);
		setProfession(3);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.villager.idle");
		return false;
	}
}
