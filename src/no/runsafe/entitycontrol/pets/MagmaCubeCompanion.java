package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.World;

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
