package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;

public class BatCompanion extends CompanionPetAnimal
{
	public BatCompanion(IWorld world)
	{
		super(world);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.bat.idle";
	}
}
