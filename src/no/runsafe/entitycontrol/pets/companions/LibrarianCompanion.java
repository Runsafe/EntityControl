package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetVillager;
import no.runsafe.framework.api.IWorld;

public class LibrarianCompanion extends CompanionPetVillager
{
	public LibrarianCompanion(IWorld world)
	{
		super(world);
		setProfession(1);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.villager.idle";
	}
}
