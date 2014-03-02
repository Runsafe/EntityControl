package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.World;

public class CustomZombiePigman extends CustomEntity
{
	public CustomZombiePigman(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return "mob.zombiepig.zpig";
	}

	@Override
	protected String aT()
	{
		return "mob.zombiepig.zpighurt";
	}

	@Override
	protected String aU()
	{
		return "mob.zombiepig.zpigdeath";
	}
}
