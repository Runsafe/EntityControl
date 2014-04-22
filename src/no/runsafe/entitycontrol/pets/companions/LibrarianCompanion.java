package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.World;
import no.runsafe.entitycontrol.pets.CompanionPetVillager;

public class LibrarianCompanion extends CompanionPetVillager
{
	public LibrarianCompanion(World world)
	{
		super(world);
		setProfession(1);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.villager.idle");
		return false;
	}
}
