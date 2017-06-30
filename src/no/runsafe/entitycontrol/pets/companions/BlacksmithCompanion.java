package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetVillager;
import no.runsafe.framework.api.IWorld;

public class BlacksmithCompanion extends CompanionPetVillager
{
	public BlacksmithCompanion(IWorld world)
	{
		super(world);
		setProfession(3);
	}
}
