package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Sound;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Random;

public class CompanionPetHumanoid extends EntityZombie implements ICompanionPet
{
	/**
	 * Constructor for CompanionPetHumanoid
	 * @param world World object is created in
	 */
	public CompanionPetHumanoid(IWorld world)
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
		setBaby(true);

		/*
		 * Silence default sounds.
		 * v1_9_R2: c
		 * v1_10_R1 and up: setSilent
		 */
		b(false);
	}

	/**
	 * Sets the player the object will follow.
	 * @param player Player to follow.
	 */
	public void setFollowingPlayer(IPlayer player)
	{
		this.player = player;
		goalSelector.a(1, new PathfinderGoalFollowPlayer(player, this));
	}

	/**
	 * Entity base tick.
	 * Names of this method in various spigot versions:
	 * v1_8_R3: K
	 * v1_9_R2/v1_10_R1/v1_11_R1: U
	 * v1_12_R1: Y
	 */
	@Override
	public void K()
	{
		super.K();

		if (randomThingTicks > 0)
			randomThingTicks--;
		else
			randomThing();

		if (randomThingProgress > 1)
			randomThingProgress--;
		else if (randomThingProgress == 1)
			removeRandomThings();
	}

	protected void randomThing()
	{
		randomThingTicks = 12000;
		randomThingProgress = 1200;
	}

	private void removeRandomThings()
	{
		randomThingProgress = 0;
		setEquipment(0, null);
		setEquipment(1, null);
		setEquipment(2, null);
		setEquipment(3, null);
		setEquipment(4, null);
	}

	private int randomThingTicks = 12000;
	private int randomThingProgress = 0;
	@Nonnull
	protected IPlayer player;
	protected final Random random = new Random();
}
