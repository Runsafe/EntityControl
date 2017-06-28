package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class PigCompanion extends CompanionPetAnimal
{
	public PigCompanion(World world)
	{
		super(world);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.pig.say";
	}
}
