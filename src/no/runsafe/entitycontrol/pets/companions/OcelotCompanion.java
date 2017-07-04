package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.entitycontrol.pets.CompanionHandler;
import no.runsafe.entitycontrol.pets.ICompanionPet;
import no.runsafe.entitycontrol.pets.PathfinderGoalFollowPlayer;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import java.lang.reflect.Field;

public class OcelotCompanion extends EntityOcelot implements ICompanionPet
{
	public OcelotCompanion(IWorld world)
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

		this.world = world;
		goalSelector.a(0, new PathfinderGoalFloat(this));
		setAgeRaw(Integer.MIN_VALUE);

		/*
		 * Silence default sounds.
		 * v1_9_R2: c
		 * v1_10_R1 and up: setSilent
		 */
		b(false);
	}

	public void setFollowingPlayer(IPlayer player)
	{
		this.player = player;
		goalSelector.a(1, new PathfinderGoalFollowPlayer(this.player, this));
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

		if (player == null || player.isDead() || !world.equals(player.getWorld()) || !CompanionHandler.entityIsSummoned(getId()))
			dead = true;
	}

	private IWorld world;
	protected IPlayer player;
}
