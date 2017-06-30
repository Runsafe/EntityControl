package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.minecraft.Sound;

public class MagmaCubeCompanion extends CompanionPetAnimal
{
	public MagmaCubeCompanion(IWorld world)
	{
		super(world);
		this.datawatcher.watch(16, (byte) 1);
	}

	@Override
	public Sound getInteractSound()
	{
		return Sound.Creature.MagmaCube.Walk;
	}
}
