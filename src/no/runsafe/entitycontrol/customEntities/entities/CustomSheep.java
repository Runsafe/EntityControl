package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.World;

public class CustomSheep extends CustomEntity
{
	public CustomSheep(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return "mob.sheep.say";
	}

	@Override
	protected String aT()
	{
		return "mob.sheep.say";
	}

	@Override
	protected String aU()
	{
		return "mob.sheep.say";
	}

	@Override
	protected void a(int i, int j, int k, Block block)
	{
		this.makeSound("mob.sheep.step", 0.15F, 1.0F);
	}
}
