package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.World;

public class CustomSilverfish extends CustomEntity
{
	public CustomSilverfish(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return "mob.silverfish.say";
	}

	@Override
	protected String aT()
	{
		return "mob.silverfish.hit";
	}

	@Override
	protected String aU()
	{
		return "mob.silverfish.kill";
	}

	@Override
	protected void a(int i, int j, int k, Block block)
	{
		this.makeSound("mob.silverfish.step", 0.15F, 1.0F);
	}
}
