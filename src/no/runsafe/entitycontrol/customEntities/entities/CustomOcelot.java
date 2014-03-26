package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R2.World;

public class CustomOcelot extends CustomEntity
{
	public CustomOcelot(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return this.random.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow";
	}

	@Override
	protected String aT()
	{
		return "mob.cat.hit";
	}

	@Override
	protected String aS()
	{
		return "mob.cat.hit";
	}
}
