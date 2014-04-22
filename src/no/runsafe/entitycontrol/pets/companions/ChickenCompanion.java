package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class ChickenCompanion extends CompanionPetAnimal
{
	public ChickenCompanion(World world)
	{
		super(world);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.chicken.say");
		return false;
	}
}
