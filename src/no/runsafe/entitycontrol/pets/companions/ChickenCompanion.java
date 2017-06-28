package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;

public class ChickenCompanion extends CompanionPetAnimal
{
	public ChickenCompanion(IWorld world)
	{
		super(world);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.chicken.say";
	}
}
