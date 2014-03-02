package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.UnsafeList;

import java.lang.reflect.Field;

public abstract class CustomEntity extends EntityPig
{
	public CustomEntity(World world)
	{
		super(world);

		try
		{
			Field gsa = PathfinderGoalSelector.class.getDeclaredField("a");
			gsa.setAccessible(true);

			gsa.set(this.goalSelector, new UnsafeList());
			gsa.set(this.targetSelector, new UnsafeList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
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
		if (canMove)
			super.move(d0, d1, d2);
	}

	@Override
	public boolean damageEntity(DamageSource damageSource, float v)
	{
		if (!invincible)
			return super.damageEntity(damageSource, v);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		return false;
	}

	@Override
	protected void dropDeathLoot(boolean flag, int i)
	{
		// Do nothing.
	}

	@Override
	public EntityAgeable createChild(EntityAgeable entityageable)
	{
		return null;
	}

	public void setCanMove(boolean canMove)
	{
		this.canMove = canMove;
	}

	public void setInvincible(boolean invincible)
	{
		this.invincible = invincible;
	}

	public PathfinderGoalSelector getGoalSelector()
	{
		return goalSelector;
	}

	public PathfinderGoalSelector getTargetSelector()
	{
		return targetSelector;
	}

	private boolean canMove = true;
	private boolean invincible = false;
}
