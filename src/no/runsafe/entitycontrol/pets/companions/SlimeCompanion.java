package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class SlimeCompanion extends CompanionPetAnimal
{
	public SlimeCompanion(World world)
	{
		super(world);
		this.datawatcher.watch(16, (byte) 1);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.slime.small");
		return false;
	}
}
