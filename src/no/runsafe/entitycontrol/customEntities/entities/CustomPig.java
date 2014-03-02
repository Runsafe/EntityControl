package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.World;

public class CustomPig extends CustomEntity
{
	public CustomPig(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return "mob.pig.say";
	}

	@Override
	protected String aT()
	{
		return "mob.pig.say";
	}

	@Override
	protected String aU()
	{
		return "mob.pig.death";
	}

	@Override
	protected void a(int i, int j, int k, Block block)
	{
		this.makeSound("mob.pig.step", 0.15F, 1.0F);
	}
}
