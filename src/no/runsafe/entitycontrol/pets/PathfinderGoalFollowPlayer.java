package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;

public class PathfinderGoalFollowPlayer extends PathfinderGoal
{
	/**
	 * Constructor for PathfinderGoalFollowPlayer
	 * @param player Player to follow
	 * @param entity This entity.
	 * @param entitySpeed a double.
	 * @param sqrtPlayerDistanceLimit Sqrt of the distance before entity will run to player.
	 * @param sqrtClosestPointToPlayer Sqrt of the distance before entity will stop running to player.
	 */
	public PathfinderGoalFollowPlayer(EntityPlayer player, EntityInsentient entity, double entitySpeed, float sqrtPlayerDistanceLimit, float sqrtClosestPointToPlayer)
	{
		this.entity = entity;
		this.world = entity.world;
		this.player = player;
		this.speed = entitySpeed;
		this.entityNavigation = (Navigation) entity.getNavigation();
		this.playerDistanceLimit = sqrtPlayerDistanceLimit;
		this.closestPointToPlayer = sqrtClosestPointToPlayer;
		this.a(3); // I have no idea what this does.
	}

	/**
	 * Check if player is too far away so the companion can run to them.
	 * @return True if the player is further away than playerDistanceLimit squared.
	 */
	@Override
	public boolean a()
	{
		/*
		* Function names:
		* v1_7_R3: .f(player)
		* v1_8_R3: .g(player)
		* v1_9_R2: .g(player)
		* v1_10_R1: .g(player)
		* This function returns distance squared
		*/
		return !(player == null || entity.g(player) < (double) (playerDistanceLimit * playerDistanceLimit));
	}

	/**
	 * Check if companion should keep running towards the player.
	 * @return True when distance from player is further than closestPointToPlayer squared.
	 */
	@Override
	public boolean b()
	{
		/*
		* Function names:
		* v1_7_R3: g(), .f(player)
		* v1_8_R3: m(), .g(player)
		* v1_9_R2: n(), .g(player)
		* v1_10_R1: n(), .g(player)
		* First function returns true if the path is null OR when path has reached a certain point
		* Second function returns player distance squared
		*/
		return !entityNavigation.m() && entity.g(player) > (double) (closestPointToPlayer * closestPointToPlayer);
	}

	@Override
	public void c()
	{
		/*
		* Function names:
		* v1_7_R3: a(), a(false)
		* v1_8_R3: e(), a(false)
		* v1_9_R2: f(), c(false)
		* v1_10_R1: f(), c(false)
		*/
		h = 0;
		Navigation entityNewNavigation = (Navigation) entity.getNavigation();
		i = (entityNewNavigation).e();
		(entityNewNavigation).a(false);
	}

	@Override
	public void d()
	{
		/*
		* Function names:
		* v1_7_R3: .h(), a(this.i)
		* v1_8_R3: .n(), a(this.i)
		* v1_9_R2: .o(), c(this.i)
		* v1_10_R1: .o(), c(this.i)
		* First function sets path equal to null
		*/
		entityNavigation.n();
		((Navigation) entity.getNavigation()).a(this.i);
	}

	@Override
	public void e()
	{
		final float Z_PITCH = 40;  //Head tilt pitch
		final float SPEED = 10.0F;
		entity.getControllerLook().a(player, SPEED, Z_PITCH);

		if (--this.h > 0)
			return;

		this.h = 10;
		if (this.entityNavigation.a(player, this.speed))
			return;

		/*
		* Function names:
		* v1_7_R3: .bN()
		* v1_8_R3: .cc()
		* v1_9_R2 and up: .isLeashed()
		*/
		if (entity.cc())//If entity is leashed, stop function.
			return;

		/*
		* Function names:
		* v1_7_R3: .f(player)
		* v1_8_R3: .g(player)
		* v1_9_R2: .g(player)
		* v1_10_R1: .g(player)
		* This function returns distance squared
		*/
		if (entity.g(player) >= 144.0D)//If player is more than 12 units(Blocks?) away
		{
			int blockLocX = MathHelper.floor(player.locX) - 2;
			int blockLocZ = MathHelper.floor(player.locZ) - 2;
			int blockLocY = MathHelper.floor(player.getBoundingBox().b);

			//Check blocks around the current object in a hollow 3x3block square
			for (int indexX = 0; indexX <= 4; ++indexX)
			{
				for (int indexZ = 0; indexZ <= 4; ++indexZ)
				{
					if ((indexX < 1 || indexZ < 1 || indexX > 3 || indexZ > 3)
						&& World.a(world, new BlockPosition(blockLocX + indexX, blockLocY - 1, blockLocZ + indexZ))
						&& !world.getType(new BlockPosition(blockLocX + indexX, blockLocY - 1, blockLocZ + indexZ)).getBlock().isOccluding()
						&& !world.getType(new BlockPosition(blockLocX + indexX, blockLocY - 1, blockLocZ + indexZ)).getBlock().isOccluding()
					)
					{
						//Move entity.
						entity.setPositionRotation(
								(double) ((float) (blockLocX + indexX) + 0.5F),
								(double) blockLocY,
								(double) ((float) (blockLocZ + indexZ) + 0.5F),
								entity.yaw,
								entity.pitch
						);

						/*
						* Function names:
						* v1_7_R3: .h()
						* v1_8_R3: .n()
						* v1_9_R2: .o()
						* v1_10_R1: .o()
						* Set path equal to null
						*/
						this.entityNavigation.n();
						return;
					}
				}
			}
		}
	}

	private EntityInsentient entity;
	private EntityPlayer player;
	private World world;
	private double speed;
	private Navigation entityNavigation;
	private int h;
	private float closestPointToPlayer; // Sqrt of the distance before entity will stop running to player.
	private float playerDistanceLimit; // Sqrt of the distance before entity will run to player.
	private boolean i;
}
