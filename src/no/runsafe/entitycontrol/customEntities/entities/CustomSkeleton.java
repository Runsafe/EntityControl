package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.World;

public class CustomSkeleton extends CustomEntity
{
	public CustomSkeleton(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return "mob.skeleton.say";
	}

	@Override
	protected String aT()
	{
		return "mob.skeleton.hurt";
	}

	@Override
	protected String aU()
	{
		return "mob.skeleton.death";
	}

	@Override
	protected void a(int i, int j, int k, Block block)
	{
		this.makeSound("mob.skeleton.step", 0.15F, 1.0F);
	}
}
