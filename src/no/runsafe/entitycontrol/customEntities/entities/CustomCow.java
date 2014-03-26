package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R2.World;

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
	protected String aS()
	{
		return "mob.cow.hurt";
	}
}
