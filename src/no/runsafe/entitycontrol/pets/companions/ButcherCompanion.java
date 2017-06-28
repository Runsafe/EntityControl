package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.World;
import no.runsafe.entitycontrol.pets.CompanionPetVillager;

public class ButcherCompanion extends CompanionPetVillager
{
	public ButcherCompanion(World world)
	{
		super(world);
		setProfession(4);
	}

	@Override
	public String getInteractSound()
	{
		return "mob.villager.idle";
	}
}
