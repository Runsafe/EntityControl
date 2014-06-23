package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_7_R3.EntityHuman;
import net.minecraft.server.v1_7_R3.World;
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
