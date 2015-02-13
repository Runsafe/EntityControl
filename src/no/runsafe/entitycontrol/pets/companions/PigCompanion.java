package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class PigCompanion extends CompanionPetAnimal
{
	public PigCompanion(World world)
	{
		super(world);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.pig.say");
		return false;
	}
}
