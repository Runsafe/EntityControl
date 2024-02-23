package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_12_R1.*;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.entity.IBat;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.entity.ISlime;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;

import javax.annotation.Nonnull;

import static java.lang.Math.abs;
import static java.lang.Math.atan2;
import static java.lang.Math.signum;
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
		this.world = entity.getWorld();
		this.player = player;
		this.rawPlayer= ObjectUnwrapper.getMinecraft(player);

		if (rawEntity != null)
			this.entityNavigation = (Navigation) this.rawEntity.getNavigation();
		else
			this.entityNavigation = null;

		this.a(3); // Something to do with whether certain tasks can run concurrently.

		if (entity instanceof ISlime)
			speed = 2.5;
	}

	/**
	 * Check if player is too far away so the companion can run to them.
	 * Method name stays the same up to v1_12_R1.
	 * @return If we should being executing.
	 */
	@Override
	public boolean a()
	{
		float playerDistanceLimit = 2F; // Distance before entity will run to player.
		double distance = getOwnerDistance();
		return Double.isNaN(distance) || distance >= playerDistanceLimit;
	}

	/**
	 * Check if companion should keep running towards the player.
	 * Method name stays the same up to v1_12_R1.
	 * @return If an already running instance should continue running.
	 */
	@Override
	public boolean b()
	{
		/*
		 * Method names:
		 * v1_12_R1: o
		 * Check if path is null or the end is reached.
		 */
		return !entityNavigation.o() && getOwnerDistance() > closestPointToPlayer;
	}

	/**
	 * Start executing.
	 * Method name stays the same up to v1_12_R1.
	 */
	@Override
	public void c()
	{
		playerTeleportTimer = 0;
	}

	/**
	 * Resets the companion's path.
	 * Method name stays the same up to v1_12_R1.
	 */
	@Override
	public void d()
	{
		/*
		* v1_12_R1: .p()
		* sets path equal to null.
		*/
		entityNavigation.p();
	}

	/**
	 * Teleport to the companion's owner when too far away.
	 * Method name stays the same up to v1_12_R1.
	 */
	@Override
	public void e()
	{
		if (entity instanceof IBat)
		{
			double xDistance = rawPlayer.locX - rawEntity.locX;
			double zDistance = rawPlayer.locZ - rawEntity.locZ;
			double newLocX = rawEntity.locX;
			double newLocZ = rawEntity.locZ;

			if (abs(xDistance) > closestPointToPlayer + 0.3)
				newLocX += 1 * signum(xDistance);
			else if (abs(xDistance) < closestPointToPlayer - 0.3)
				newLocX -= 0.2 * signum(xDistance);

			if (abs(zDistance) > closestPointToPlayer + 0.3)
				newLocZ += 1 * signum(zDistance);
			else if (abs(zDistance) < closestPointToPlayer - 0.3)
				newLocZ -= 0.2 * signum(zDistance);

			rawEntity.setPositionRotation(
				newLocX,
				rawPlayer.locY + 1.5,
				newLocZ,
				(float) toDegrees(atan2(rawEntity.locX - rawPlayer.locX, rawEntity.locZ - rawPlayer.locZ)),
				rawEntity.pitch
			);
		}
		else if (entity instanceof ISlime)
			rawEntity.yaw = (float) toDegrees(atan2(rawEntity.locX - rawPlayer.locX, rawEntity.locZ - rawPlayer.locZ));

		final float HEAD_TILT_PITCH = 40;
		final float SPEED = 10.0F;
		rawEntity.getControllerLook().a(rawPlayer, SPEED, HEAD_TILT_PITCH);

		if (--this.playerTeleportTimer > 0)
			return;

		this.playerTeleportTimer = 10;
		if (this.entityNavigation.a(rawPlayer, this.speed))
			return;

		if (rawEntity.isLeashed()) // Stop if entity is leashed.
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
					// Check for solid block to teleport on
					&& !world.getBlockAt(blockLocX + indexX, blockLocY - 1, blockLocZ + indexZ).canPassThrough()
					// Check that the companion can exist within this location
					&& world.getBlockAt(blockLocX + indexX, blockLocY, blockLocZ + indexZ).canPassThrough()
					&& world.getBlockAt(blockLocX + indexX, blockLocY + 1, blockLocZ + indexZ).canPassThrough()
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
				 * v1_12_R1: .p()
				 * Set path equal to null
				 */
				this.entityNavigation.p();
				return;
			}
		}
	}

	/**
	 * @return Distance between companion and its owner.
	 */
	private double getOwnerDistance()
	{
		ILocation location = player.getLocation();
		if (location == null)
			return Double.NaN;
		return player.getLocation().distance(entity.getLocation());
	}

	private final ILivingEntity entity;
	private final EntityInsentient rawEntity;
	private final EntityPlayer rawPlayer;
	@Nonnull
	private final IPlayer player;
	private final IWorld world;
	private double speed = 1;
	private final Navigation entityNavigation;
	private int playerTeleportTimer;
	private final float closestPointToPlayer = 2F; // Distance before entity will stop running to player.
}
