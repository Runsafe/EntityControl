package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.World;

public class CustomIronGolem extends CustomEntity
{
	public CustomIronGolem(World world)
	{
		super(world);
	}

	@Override
	protected String aT()
	{
		return "mob.irongolem.hit";
	}

	@Override
	protected String aU()
	{
		return "mob.irongolem.death";
	}

	@Override
	protected void a(int i, int j, int k, Block block)
	{
		this.makeSound("mob.irongolem.walk", 1.0F, 1.0F);
	}
}
