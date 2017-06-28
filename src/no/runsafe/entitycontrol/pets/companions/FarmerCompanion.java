package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetVillager;
import no.runsafe.framework.api.IWorld;

public class FarmerCompanion extends CompanionPetVillager
{
	public FarmerCompanion(IWorld world)
	{
		super(world);
		setProfession(0);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.villager.idle";
	}
}
