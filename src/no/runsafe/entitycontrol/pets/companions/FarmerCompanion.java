package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.World;
import no.runsafe.entitycontrol.pets.CompanionPetVillager;

public class FarmerCompanion extends CompanionPetVillager
{
	public FarmerCompanion(World world)
	{
		super(world);
		setProfession(0);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.villager.idle");
		return false;
	}
}
