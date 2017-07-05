package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.entity.ISlime;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;

import javax.annotation.Nonnull;

import static java.lang.Math.atan2;
import static java.lang.Math.toDegrees;

public class PathfinderGoalFollowPlayer extends PathfinderGoal
{
	/**
	 * Constructor for PathfinderGoalFollowPlayer
	 * @param player Player to follow
	 * @param entity This entity.
	 */
	public PathfinderGoalFollowPlayer(@Nonnull IPlayer player, ILivingEntity entity)
	{
		this.entity = entity;
		this.rawEntity = ((EntityInsentient) ObjectUnwrapper.getMinecraft(entity));
		this.world = this.rawEntity.world;
		this.player = player;
		this.rawPlayer= ObjectUnwrapper.getMinecraft(player);
		this.entityNavigation = (Navigation) this.rawEntity.getNavigation();
		this.a(3); // Something to do with whether or not certain tasks can run concurrently.

		if (entity instanceof ISlime)
			speed = 2.5;
	}

	/**
	 * Check if player is too far away so the companion can run to them.
	 * Method name stays the same up to v1_12_R1.
	 * @return True if the player is further away than playerDistanceLimit squared.
	 */
	@Override
	public boolean a()
	{
		return !(getOwnerDistance() < playerDistanceLimit);
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
		playerTeleportTimer = 0;
		Navigation entityNewNavigation = (Navigation) rawEntity.getNavigation();
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
		((Navigation) rawEntity.getNavigation()).a(this.i);
	}

	/**
	 * Teleport to the companion's owner when too far away.
	 * Method name stays the same up to v1_12_R1.
	 */
	@Override
	public void e()
	{
		if (entity instanceof ISlime)
			rawEntity.yaw = (float) (180 - toDegrees(atan2(rawEntity.locX - rawPlayer.locX, rawEntity.locZ - rawPlayer.locZ)));

		final float HEAD_TILT_PITCH = 40;
		final float SPEED = 10.0F;
		rawEntity.getControllerLook().a(rawPlayer, SPEED, HEAD_TILT_PITCH);

		if (--this.playerTeleportTimer > 0)
			return;

		this.playerTeleportTimer = 10;
		if (this.entityNavigation.a(rawPlayer, this.speed))
			return;

		/*
		 * Method names:
		 * v1_8_R3: .cc()
		 * v1_9_R2 and up: .isLeashed()
		 */
		if (rawEntity.cc()) // Stop if entity is leashed.
			return;

		if (getOwnerDistance() < 144.0D) // Check if the companion owner is 144 blocks away.
			return;

		// Get the companion owner's location.
		int blockLocX = MathHelper.floor(rawPlayer.locX) - 2;
		int blockLocZ = MathHelper.floor(rawPlayer.locZ) - 2;
		int blockLocY = MathHelper.floor(rawPlayer.getBoundingBox().b);

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
				rawEntity.setPositionRotation(
					blockLocX + indexX + 0.5F,
					blockLocY,
					blockLocZ + indexZ + 0.5F,
					rawEntity.yaw,
					rawEntity.pitch
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
		return player.getLocation().distance(entity.getLocation());
	}

	private ILivingEntity entity;
	private EntityInsentient rawEntity;
	private EntityPlayer rawPlayer;
	@Nonnull
	private IPlayer player;
	private World world;
	private double speed = 1;
	private Navigation entityNavigation;
	private int playerTeleportTimer;
	private float closestPointToPlayer = 2F; // Distance before entity will stop running to player.
	private float playerDistanceLimit = 2F; // Distance before entity will run to player.
	private boolean i; // Something to do with if the entity is traveling through water or not.
}
