package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.minecraft.Sound;

public class SilverfishCompanion extends CompanionPetAnimal
{
	public SilverfishCompanion(IWorld world)
	{
		super(world);
	}

	@Override
	public Sound getInteractSound()
	{
		return Sound.Creature.Silverfish.Idle;
	}
}
