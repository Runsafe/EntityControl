package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.Block;
import net.minecraft.server.v1_7_R1.EntityPig;
import net.minecraft.server.v1_7_R1.World;

public abstract class CustomEntity extends EntityPig
{
	public CustomEntity(World world)
	{
		super(world);
	}

	@Override
	protected String t()
	{
		return "none";
	}

	@Override
	protected String aT()
	{
		return "none";
	}

	@Override
	protected String aU()
	{
		return "none";
	}

	@Override
	protected void a(int i, int j, int k, Block block)
	{
		// Do nothing!
	}

	@Override
	public void move(double d0, double d1, double d2)
	{
		if (!canMove)
			super.move(d0, d1, d2);
	}

	public void setCanMove(boolean canMove)
	{
		this.canMove = canMove;
	}

	private boolean canMove = true;
}
