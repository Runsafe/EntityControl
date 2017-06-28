package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class ChickenCompanion extends CompanionPetAnimal
{
	public ChickenCompanion(World world)
	{
		super(world);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.chicken.say";
	}
}
