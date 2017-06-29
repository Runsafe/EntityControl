package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import java.lang.reflect.Field;
import java.util.Random;

public class CompanionPetHumanoid extends EntityZombie implements ICompanionPet
{
	/**
	 * Constructor for CompanionPetHumanoid
	 * @param world World object is created in
	 */
	public CompanionPetHumanoid(IWorld world)
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
		setBaby(true);
	}

	/**
	 * Sets the player the object will follow.
	 * @param player Player to follow.
	 */
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
	 * In spigot checks if player can give this object a golden apple if it's a zombie villager.
	 * Here it does nothing.
	 * Function name might not change in future spigot versions.
	 * @param entityhuman Player interacting with object.
	 * @return True if successful, otherwise false. Always false here.
	 */
	@Override
	public boolean a(EntityHuman entityhuman)
	{
		// Interact with player.
		playSound(getInteractSound());
		return false;
	}

	/**
	 * Gets the sound to be made when right clicked by a player.
	 * @return Sound to make when right clicked by a player.
	 */
	public String getInteractSound()
	{
		return "";
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

		if (player == null || !player.isAlive() || !player.world.worldData.getName().equals(world.worldData.getName()) || !CompanionHandler.entityIsSummoned(this))
			dead = true;

		if (randomThingTicks > 0)
			randomThingTicks--;
		else
			randomThing();

		if (randomThingProgress > 1)
			randomThingProgress--;
		else if (randomThingProgress == 1)
			removeRandomThings();
	}

	protected void randomThing()
	{
		randomThingTicks = 12000;
		randomThingProgress = 1200;
	}

	private void removeRandomThings()
	{
		randomThingProgress = 0;
		setEquipment(0, null);
		setEquipment(1, null);
		setEquipment(2, null);
		setEquipment(3, null);
		setEquipment(4, null);
	}

	/**
	 * Plays a sound.
	 * @param sound sound name to play.
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
	private int randomThingTicks = 12000;
	private int randomThingProgress = 0;
	protected EntityPlayer player;
	protected final Random random = new Random();
}
