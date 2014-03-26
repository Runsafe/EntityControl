package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R2.Block;
import net.minecraft.server.v1_7_R2.World;

public class CustomWolf extends CustomEntity
{
	public CustomWolf(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		int ran = random.nextInt(3);

		if (ran == 0)
			return "mob.wolf.whine";
		else if (ran == 1)
			return "mob.wolf.panting";

		return "mob.wolf.bark";
	}

	@Override
	protected String aT()
	{
		return "mob.wolf.hurt";
	}

	@Override
	protected String aS()
	{
		return "mob.wolf.death";
	}

	@Override
	protected void a(int i, int j, int k, Block block)
	{
		this.makeSound("mob.wolf.step", 0.15F, 1.0F);
	}
}
