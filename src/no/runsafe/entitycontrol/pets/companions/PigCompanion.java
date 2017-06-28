package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;

public class PigCompanion extends CompanionPetAnimal
{
	public PigCompanion(IWorld world)
	{
		super(world);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.pig.say";
	}
}
