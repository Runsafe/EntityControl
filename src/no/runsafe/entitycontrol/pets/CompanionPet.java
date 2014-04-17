package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityZombie;
import net.minecraft.server.v1_7_R2.PathfinderGoalSelector;
import net.minecraft.server.v1_7_R2.World;
import org.bukkit.craftbukkit.v1_7_R2.util.UnsafeList;

import java.lang.reflect.Field;

public class CompanionPet extends EntityZombie
{
	public CompanionPet(World world)
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
		return "none";
	}

	@Override
	protected String aT()
	{
		return "none";
	}

	@Override
	protected String aS()
	{
		return "none";
	}
}
