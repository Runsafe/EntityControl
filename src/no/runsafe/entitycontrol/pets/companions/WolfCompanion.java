package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class WolfCompanion extends CompanionPetAnimal
{
	public WolfCompanion(World world)
	{
		super(world);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.wolf.growl");
		return false;
	}
}
