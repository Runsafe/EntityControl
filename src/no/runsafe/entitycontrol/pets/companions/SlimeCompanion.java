package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;

public class SlimeCompanion extends CompanionPetAnimal
{
	public SlimeCompanion(IWorld world)
	{
		super(world);
		this.datawatcher.watch(16, (byte) 1);
	}
}
