package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;

public class SilverfishCompanion extends CompanionPetAnimal
{
	public SilverfishCompanion(IWorld world)
	{
		super(world);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.silverfish.say";
	}
}
