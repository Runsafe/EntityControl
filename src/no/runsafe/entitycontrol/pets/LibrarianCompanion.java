package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.World;

public class LibrarianCompanion extends CompanionPetAnimal
{
	public LibrarianCompanion(World world)
	{
		super(world);
		this.datawatcher.watch(16, 1);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.villager.idle");
		return false;
	}
}
