package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.World;
import no.runsafe.entitycontrol.pets.CompanionPetVillager;

public class PriestCompanion extends CompanionPetVillager
{
	public PriestCompanion(World world)
	{
		super(world);
		setProfession(2);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.villager.idle");
		return false;
	}
}
