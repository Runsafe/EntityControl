package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.World;

public class CustomWitch extends CustomEntity
{
	public CustomWitch(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return "mob.witch.idle";
	}

	@Override
	protected String aT()
	{
		return "mob.witch.hurt";
	}

	@Override
	protected String aU()
	{
		return "mob.witch.death";
	}

}
