package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R3.*;

public class PathfinderGoalFollowPlayer extends PathfinderGoal
{
	public PathfinderGoalFollowPlayer(EntityPlayer player, EntityInsentient entity, double d0, float f, float f1)
	{
		this.entity = entity;
		this.world = entity.world;
		this.player = player;
		this.f = d0;
		this.g = entity.getNavigation();
		this.c = f;
		this.b = f1;
		this.a(3); // I have no idea what this does.
	}

	@Override
	public boolean a()
	{
		return !(player == null || entity.f(player) < (double) (c * c));
	}

	@Override
	public boolean b()
	{
		return !g.g() && entity.f(player) > (double) (b * b);
	}

	@Override
	public void c()
	{
		h = 0;
		i = entity.getNavigation().a();
		entity.getNavigation().a(false);
	}

	@Override
	public void d()
	{
		g.h();
		entity.getNavigation().a(this.i);
	}

	@Override
	public void e()
	{
		entity.getControllerLook().a(player, 10.0F, (float) entity.bv());
		if (--this.h <= 0)
		{
			this.h = 10;
			if (!this.g.a(player, this.f))
			{
				if (!entity.bN())
				{
					if (entity.f(player) >= 144.0D)
					{
						int i = MathHelper.floor(player.locX) - 2;
						int j = MathHelper.floor(player.locZ) - 2;
						int k = MathHelper.floor(player.boundingBox.b);

						for (int l = 0; l <= 4; ++l)
						{
							for (int i1 = 0; i1 <= 4; ++i1)
							{
								if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && World.a(world, i + l, k - 1, j + i1) && !world.getType(i + l, k, j + i1).r() && !world.getType(i + l, k + 1, j + i1).r())
								{
									entity.setPositionRotation((double) ((float) (i + l) + 0.5F), (double) k, (double) ((float) (j + i1) + 0.5F), entity.yaw, entity.pitch);
									this.g.h();
									return;
								}
							}
						}
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
