package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_7_R3.EntityHuman;
import net.minecraft.server.v1_7_R3.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class CowCompanion extends CompanionPetAnimal
{
	public CowCompanion(World world)
	{
		super(world);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.cow.say");
		return false;
	}
}
