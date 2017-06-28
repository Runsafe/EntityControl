package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetVillager;
import no.runsafe.framework.api.IWorld;

public class ButcherCompanion extends CompanionPetVillager
{
	public ButcherCompanion(IWorld world)
	{
		super(world);
		setProfession(4);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.villager.idle";
	}
}
