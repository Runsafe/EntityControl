package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.World;
import no.runsafe.entitycontrol.pets.CompanionPetAnimal;

public class MagmaCubeCompanion extends CompanionPetAnimal
{
	public MagmaCubeCompanion(World world)
	{
		super(world);
		this.datawatcher.watch(16, (byte) 1);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.magmacube.small");
		return false;
	}
}
