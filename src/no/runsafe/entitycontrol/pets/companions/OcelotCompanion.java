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

		goalSelector.a(0, new PathfinderGoalFloat(this));
		setAge(-1000);
	}

	@Override
	public void setLeashHolder(Entity entity, boolean flag)
	{
		// No.
	}

	public void setFollowingPlayer(IPlayer player)
	{
		this.player = ObjectUnwrapper.getMinecraft(player);
		goalSelector.a(1, new PathfinderGoalFollowPlayer(this.player, this, 1.0D, 2F, 2F));
	}

	/**
	 * Volume to make noises at.
	 * Names of this method in various spigot versions:
	 * v1_8_R3: bB
	 * v1_9_R2: ce
	 * v1_10_R1: ch
	 * v1_11_R1: ci
	 * @return Volume.
	 */
	@Override
	protected float bB()
	{
		return 0;
	}

	@Override
	public boolean damageEntity(DamageSource damagesource, float f)
	{
		return false;
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.cat.hitt");
		return false;
	}

	@Override
	protected void dropDeathLoot(boolean flag, int i)
	{
		// Do nothing! We don't want loot.
	}

	@Override
	public void K()
	{
		// Entity base tick
		super.K();

		if (soundTicks > 0)
			soundTicks--;

		if (isAlive())
			setAge(-1000);

		if (player == null || !player.isAlive() || !player.world.worldData.getName().equals(world.worldData.getName()) || !CompanionHandler.entityIsSummoned(this))
			dead = true;
	}

	public void playSound(String sound)
	{
		if (soundTicks == 0)
		{
			final float SOUND_VOLUME = 1.0F;
			final float SOUND_PITCH = (random.nextFloat() - random.nextFloat()) * 0.2F + 1.5F;
			makeSound(sound, SOUND_VOLUME, SOUND_PITCH);
			soundTicks = 40;
		}
	}

	private int soundTicks = 0;
	protected EntityPlayer player;
}
