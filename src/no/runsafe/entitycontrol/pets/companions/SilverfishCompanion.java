package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class SilverfishCompanion extends CompanionPetAnimal
{
	public SilverfishCompanion(World world)
	{
		super(world);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.silverfish.say";
	}
}
