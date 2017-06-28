package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;

public class CowCompanion extends CompanionPetAnimal
{
	public CowCompanion(IWorld world)
	{
		super(world);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.cow.say";
	}
}
