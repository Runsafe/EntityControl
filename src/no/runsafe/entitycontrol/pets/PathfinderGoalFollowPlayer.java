package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;

public class PathfinderGoalFollowPlayer extends PathfinderGoal
{
	public PathfinderGoalFollowPlayer(EntityPlayer player, EntityInsentient entity, double d0, float f, float f1)
	{
		this.entity = entity;
		this.world = entity.world;
		this.player = player;
		this.f = d0;
		this.g = (Navigation) entity.getNavigation();
		this.c = f;
		this.b = f1;
		this.a(3); // I have no idea what this does.
	}

	/*
	* Returns true if player exists and is further away than value c
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
		* Second function returns distance squared
		*/
		return !g.m() && entity.g(player) > (double) (b * b);
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
		i = ((Navigation) entity.getNavigation()).e();
		((Navigation) entity.getNavigation()).a(false);
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
		*/
		g.n();
		((Navigation) entity.getNavigation()).a(this.i);
	}

	@Override
	public void e()
	{
		final float Z_PITCH = 40;//Likely used in changing the z pitch of where entity is looking
		entity.getControllerLook().a(player, 10.0F, Z_PITCH);

		if (--this.h > 0)
			return;

		this.h = 10;
		if (this.g.a(player, this.f))// .a(player, this.f) has the same name in 1.7 - 1.10
			return;

		/*
		* Function names:
		* v1_7_R3: .bN()
		* v1_8_R3: .cc()
		* v1_9_R2: .isLeashed()
		* v1_10_R1: .isLeashed()
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
		if (entity.g(player) >= 144.0D)//If player is more than 144 blocks away
		{
			int i = MathHelper.floor(player.locX) - 2;
			int j = MathHelper.floor(player.locZ) - 2;
			int k = MathHelper.floor(player.getBoundingBox().b);

			for (int l = 0; l <= 4; ++l)
			{
				for (int i1 = 0; i1 <= 4; ++i1)
				{
					if ((l < 1 || i1 < 1 || l > 3 || i1 > 3)
						&& World.a(world, new BlockPosition(i + l, k - 1, j + i1))
						&& !world.getType(new BlockPosition(i + l, k - 1, j + i1)).getBlock().isOccluding()
						&& !world.getType(new BlockPosition(i + l, k - 1, j + i1)).getBlock().isOccluding()
					)
					{
						entity.setPositionRotation(
								(double) ((float) (i + l) + 0.5F),
								(double) k,
								(double) ((float) (j + i1) + 0.5F),
								entity.yaw,
								entity.pitch
						);

						/*
						* Function names:
						* v1_7_R3: .h()
						* v1_8_R3: .n()
						* v1_9_R2: .o()
						* v1_10_R1: .o()
						*/
						this.g.n();
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
	private Navigation g;
	private int h;
	private float b;
	private float c;
	private boolean i;
}
