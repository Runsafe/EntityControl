package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.World;
import no.runsafe.entitycontrol.pets.CompanionPetVillager;

public class PriestCompanion extends CompanionPetVillager
{
	public PriestCompanion(World world)
	{
		super(world);
		setProfession(2);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.villager.idle";
	}
}
