package no.runsafe.entitycontrol.seat;

import net.minecraft.server.v1_7_R2.*;
import org.bukkit.craftbukkit.v1_7_R2.util.UnsafeList;

import java.lang.reflect.Field;

public class SeatEntity extends EntityPig
{
	public SeatEntity(World world)
	{
		super(world);

		// Remove all default path-finders.
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
		return "none"; // Idle sound.
	}

	@Override
	protected String aT()
	{
		return "none"; // Hurt sound.
	}

	@Override
	protected String aS()
	{
		return "none"; // Death sound.
	}

	@Override
	public boolean damageEntity(DamageSource damagesource, float f)
	{
		return false;
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		// Interact with player.
		return false;
	}

	@Override
	protected void dropDeathLoot(boolean flag, int i)
	{
		// Do nothing! We don't want loot.
	}

	@Override
	public void B()
	{
		super.B();

		if (passenger == null)
			dead = true;
	}
}