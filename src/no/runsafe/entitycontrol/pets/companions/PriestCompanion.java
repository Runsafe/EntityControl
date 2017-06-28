package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetVillager;
import no.runsafe.framework.api.IWorld;

public class PriestCompanion extends CompanionPetVillager
{
	public PriestCompanion(IWorld world)
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
