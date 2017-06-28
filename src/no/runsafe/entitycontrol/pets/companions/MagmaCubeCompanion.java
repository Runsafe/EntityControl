package no.runsafe.entitycontrol.pets.companions;

import no.runsafe.entitycontrol.pets.CompanionPetAnimal;
import no.runsafe.framework.api.IWorld;

public class MagmaCubeCompanion extends CompanionPetAnimal
{
	public MagmaCubeCompanion(IWorld world)
	{
		super(world);
		this.datawatcher.watch(16, (byte) 1);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.magmacube.small";
	}
}
