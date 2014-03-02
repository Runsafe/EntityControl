package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.World;

public class CustomCow extends CustomEntity
{
	public CustomCow(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return "mob.cow.say";
	}

	@Override
	protected String aT()
	{
		return "mob.cow.hurt";
	}

	@Override
	protected String aU()
	{
		return "mob.cow.hurt";
	}
}
