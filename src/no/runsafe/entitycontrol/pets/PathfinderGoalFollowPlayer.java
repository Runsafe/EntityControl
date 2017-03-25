package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;

public class PathfinderGoalFollowPlayer extends PathfinderGoal
{
	/**
	 * Constructor for PathfinderGoalFollowPlayer
	 * @param player Player to follow
	 * @param entity This entity.
	 * @param d0 a double.
	 * @param f a distance.
	 * @param f1 a distance.
	 */
	public PathfinderGoalFollowPlayer(EntityPlayer player, EntityInsentient entity, double d0, float f, float f1)
	{
		this.entity = entity;
		this.world = entity.world;
		this.player = player;
		this.f = d0;
		this.entityNavigation = (Navigation) entity.getNavigation();
		this.c = f;
		this.b = f1;
		this.a(3); // I have no idea what this does.
	}

	/**
	 * Check if player is too far away.
	 * @return True if player exists and is further away than value c.
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
		return !(player == null || entity.g(player) < (double) (c * c));
	}

	/**
	 *
	 * @return True when path is null or reached a certain point AND when player is further away than b.
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
		return !entityNavigation.m() && entity.g(player) > (double) (b * b);
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
		if (this.entityNavigation.a(player, this.f))
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
	private double f;
	private Navigation entityNavigation;
	private int h;
	private float b; //Distance
	private float c; //Distance
	private boolean i;
}
