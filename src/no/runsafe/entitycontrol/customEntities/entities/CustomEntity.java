package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.UnsafeList;

import java.lang.reflect.Field;

public abstract class CustomEntity extends EntitySkeleton
{
	public CustomEntity(World world)
	{
		super(world);

		try
		{
			Field gsa = PathfinderGoalSelector.class.getDeclaredField("b");
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
		return !invincible && super.damageEntity(damageSource, v);
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

	public void setCanMove(boolean canMove)
	{
		this.canMove = canMove;
	}

	public void setInvincible(boolean invincible)
	{
		this.invincible = invincible;
	}

	@Override
	public EnumMonsterType getMonsterType()
	{
		return EnumMonsterType.UNDEFINED;
	}

	@Override
	public GroupDataEntity a(GroupDataEntity groupdataentity)
	{
		return groupdataentity;
	}

	public PathfinderGoalSelector goalSelector()
	{
		return goalSelector;
	}

	public PathfinderGoalSelector targetSelector()
	{
		return targetSelector;
	}

	private boolean canMove = true;
	private boolean invincible = false;
}
