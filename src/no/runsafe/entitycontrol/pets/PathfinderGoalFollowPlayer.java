package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class PathfinderGoalFollowPlayer extends PathfinderGoal
{
	/**
	 * Constructor for PathfinderGoalFollowPlayer
	 * @param player Player to follow
	 * @param entity This entity.
	 * @param entitySpeed a double.
	 * @param inputPlayerDistanceLimit Distance before entity will run to player.
	 * @param inputClosestPointToPlayer Distance before entity will stop running to player.
	 */
	public PathfinderGoalFollowPlayer(EntityPlayer player, EntityInsentient entity, double entitySpeed, float inputPlayerDistanceLimit, float inputClosestPointToPlayer
	)
	{
		this.entity = entity;
		this.world = entity.world;
		this.player = player;
		this.speed = entitySpeed;
		this.entityNavigation = (Navigation) entity.getNavigation();
		this.playerDistanceLimit = inputPlayerDistanceLimit;
		this.closestPointToPlayer = inputClosestPointToPlayer;
		this.a(3); // Something to do with whether or not certain tasks can run concurrently.
	}

	/**
	 * Check if player is too far away so the companion can run to them.
	 * Method name stays the same up to v1_12_R1.
	 * @return True if the player is further away than playerDistanceLimit squared.
	 */
	@Override
	public boolean a()
	{
		return !(player == null || getOwnerDistance() < playerDistanceLimit);
	}

	/**
	 * Check if companion should keep running towards the player.
	 * Method name stays the same up to v1_12_R1.
	 * @return True when distance from player is further than closestPointToPlayer squared.
	 */
	@Override
	public boolean b()
	{
		/*
		 * Method names:
		 * v1_8_R3: m
		 * v1_9_R2/v1_10_R1/v1_11_R1: n
		 * v1_12_R1: o
		 * Check if path is null or the end is reached.
		 */
		return !entityNavigation.m() && getOwnerDistance() > closestPointToPlayer;
	}

	/**
	 * Start executing.
	 * Method name stays the same up to v1_12_R1.
	 */
	@Override
	public void c()
	{
		/*
		* Method names:
		* v1_8_R3: e(), a(false)
		* v1_9_R2: Both might have been removed.
		* e() gets a value and a() sets that same value.
		* Might be related to whether or not the companion is traveling in water.
		*/
		h = 0;
		Navigation entityNewNavigation = (Navigation) entity.getNavigation();
		i = (entityNewNavigation).e();
		(entityNewNavigation).a(false);
	}

	/**
	 * Resets the companion's path.
	 * Method name stays the same up to v1_12_R1.
	 */
	@Override
	public void d()
	{
		/*
		* Method names:
		* v1_8_R3: .n(), a(this.i)
		* v1_9_R2/v1_10_R1/v1_11_R1: .o(), Might have been removed.
		* v1_12_R1: .p(), ?
		* First method sets path equal to null.
		* Second method might be related to whether or not the companion is traveling in water.
		*/
		entityNavigation.n();
		((Navigation) entity.getNavigation()).a(this.i);
	}

	/**
	 * Teleport to the companion's owner when too far away.
	 * Method name stays the same up to v1_12_R1.
	 */
	@Override
	public void e()
	{
		final float Z_PITCH = 40;  // Head tilt pitch
		final float SPEED = 10.0F;
		entity.getControllerLook().a(player, SPEED, Z_PITCH);

		if (--this.h > 0)
			return;

		this.h = 10;
		if (this.entityNavigation.a(player, this.speed))
			return;

		/*
		 * Method names:
		 * v1_8_R3: .cc()
		 * v1_9_R2 and up: .isLeashed()
		 */
		if (entity.cc()) // Stop if entity is leashed.
			return;

		if (getOwnerDistance() < 144.0D) // Check if the companion owner is 144 blocks away.
			return;

		// Get the companion owner's location.
		int blockLocX = MathHelper.floor(player.locX) - 2;
		int blockLocZ = MathHelper.floor(player.locZ) - 2;
		int blockLocY = MathHelper.floor(player.getBoundingBox().b);

		// Check blocks around the companion owner in a hollow 3x3block square
		for (int indexX = 0; indexX <= 4; ++indexX)
		{
			for (int indexZ = 0; indexZ <= 4; ++indexZ)
			{
				// Make sure there's a safe block to teleport to around the player.
				if (!((indexX < 1 || indexZ < 1 || indexX > 3 || indexZ > 3)
					&& World.a(world, new BlockPosition(blockLocX + indexX, blockLocY - 1, blockLocZ + indexZ))
					&& !world.getType(new BlockPosition(blockLocX + indexX, blockLocY - 1, blockLocZ + indexZ)).getBlock().isOccluding()
					&& !world.getType(new BlockPosition(blockLocX + indexX, blockLocY - 1, blockLocZ + indexZ)).getBlock().isOccluding()
				))
					continue;

				// Teleport to the companion owner.
				entity.setPositionRotation(
					blockLocX + indexX + 0.5F,
					blockLocY,
					blockLocZ + indexZ + 0.5F,
					entity.yaw,
					entity.pitch
				);

				/*
				 * Method names:
				 * v1_8_R3: .n()
				 * v1_9_R2/v1_10_R1/v1_11_R1: .o()
				 * v1_12_R1: .p()
				 * Set path equal to null
				 */
				this.entityNavigation.n();
				return;
			}
		}
	}

	/**
	 * @return Distance between companion and its owner.
	 */
	private double getOwnerDistance()
	{
		return sqrt(
			pow(entity.locX - player.locX, 2)
			+ pow(entity.locY - player.locY, 2)
			+ pow(entity.locZ - player.locZ, 2)
		);
	}

	private EntityInsentient entity;
	private EntityPlayer player;
	private World world;
	private double speed;
	private Navigation entityNavigation;
	private int h; // Time to wait until teleporting to a player.
	private float closestPointToPlayer; // Distance before entity will stop running to player.
	private float playerDistanceLimit; // Distance before entity will run to player.
	private boolean i; // Something to do with if the entity is traveling through water or not.
}
