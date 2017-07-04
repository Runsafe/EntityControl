package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.internal.wrapper.ObjectWrapper;
import no.runsafe.framework.minecraft.Sound;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

public class CompanionPetVillager extends EntityVillager implements ICompanionPet
{
	/**
	 * Constructor for CompanionPetVillager
	 * @param world World object is created in
	 */
	public CompanionPetVillager(IWorld world)
	{
		super(ObjectUnwrapper.getMinecraft(world));

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

		goalSelector.a(0, new PathfinderGoalFloat(this));
		setAgeRaw(Integer.MIN_VALUE);

		/*
		 * Silence default sounds..
		 * v1_9_R2: c
		 * v1_10_R1 and up: setSilent
		 */
		b(false);
	}

	/**
	 * In spigot this would give birth to a baby villager.
	 * Companion pets are sterile, so they don't give birth.
	 * @param entityAgeable Nothing.
	 * @return null
	 */
	@Override
	public EntityAgeable createChild(EntityAgeable entityAgeable)
	{
		return null;
	}

	/**
	 * Sets the player the object will follow.
	 * @param player Player to follow.
	 */
	@Override
	public void setFollowingPlayer(IPlayer player)
	{
		goalSelector.a(1, new PathfinderGoalFollowPlayer(player, this));
	}
}
