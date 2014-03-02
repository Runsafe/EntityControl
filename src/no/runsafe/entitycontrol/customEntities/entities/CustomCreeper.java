package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.World;

public class CustomCreeper extends CustomEntity
{
	public CustomCreeper(World world)
	{
		super(world);
	}

	@Override
	protected String aT()
	{
		return "mob.creeper.say";
	}

	@Override
	protected String aU()
	{
		return "mob.creeper.death";
	}
}
