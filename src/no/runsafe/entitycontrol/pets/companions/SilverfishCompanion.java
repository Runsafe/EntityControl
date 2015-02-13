package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class SilverfishCompanion extends CompanionPetAnimal
{
	public SilverfishCompanion(World world)
	{
		super(world);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.silverfish.say");
		return false;
	}
}
