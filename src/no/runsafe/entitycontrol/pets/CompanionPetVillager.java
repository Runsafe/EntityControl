package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import java.lang.reflect.Field;

public class CompanionPetVillager extends EntityVillager implements ICompanionPet
{
	/**
	 * Constructor for CompanionPetVillager
	 * @param world World object is created in
	 */
	public CompanionPetVillager(World world)
	{
		super(world);

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

	/**
	 * In spigot this would give birth to a baby villager.
	 * Companion pets are sterile, so they don't give birth.
	 * @param entityAgeable Nothing.
	 * @return null
	 */
	@Override
	public EntityAgeable createChild(EntityAgeable entityAgeable)
	{
		return null;
	}

	/**
	 * Sets the player the object will follow.
	 * @param player Player to follow.
	 */
	@Override
	public void setFollowingPlayer(IPlayer player)
	{
		this.player = ObjectUnwrapper.getMinecraft(player);
		goalSelector.a(1, new PathfinderGoalFollowPlayer(this.player, this, 1.0D, 2F, 2F));
	}

	/**
	 * Play idle sound
	 * Names of this function in different spigot versions:
	 * v1_7_R3: t
	 * v1_8_R3: z
	 * v1_9_R2: G, returns SoundEffect
	 * v1_10_R1: G, returns SoundEffect
	 * @return string "none"
	 */
	@Override
	protected String z()
	{
		return "none";
	}

	/**
	 * Play death sound
	 * Names of this function in various spigot versions:
	 * v1_7_R3: aT
	 * v1_8_R3: bp
	 * v1_9_R2: bT, returns SoundEffect
	 * v1_10_R1: bW, returns SoundEffect
	 * @return string "none"
	 */
	@Override
	protected String bp()
	{
		return "none";
	}

	/**
	 * Play hurt sound
	 * Names of this function in various spigot versions:
	 * v1_7_R3: aS
	 * v1_8_R3: bo
	 * v1_9_R2: bS, returns SoundEffect
	 * v1_10_R1: bV, returns SoundEffect
	 * @return string "none"
	 */
	@Override
	protected String bo()
	{
		return "none";
	}

	/**
	 * Damages entity if possible.
	 * This entity can't be damaged.
	 * @param damagesource Source of damage
	 * @param f Amount of damage
	 * @return true if damaged, otherwise false. Always false here.
	 */
	@Override
	public boolean damageEntity(DamageSource damagesource, float f)
	{
		return false;
	}

	/**
	 * In spigot makes player interact with this object.
	 * Here it does nothing.
	 * Function name might change in future spigot versions.
	 * @param entityhuman Player interacting with object.
	 * @return True if successful, otherwise false. Always false here.
	 */
	@Override
	public boolean a(EntityHuman entityhuman)
	{
		// Interact with player.
		return false;
	}

	/**
	 * Decides amount of loot to drop on death and drops it.
	 * Object will never drop loot.
	 * @param flag Nothing
	 * @param i Nothing / Number of items to drop.
	 */
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

	/**
	 * Plays a sound.
	 * @param sound sound name to play
	 */
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
