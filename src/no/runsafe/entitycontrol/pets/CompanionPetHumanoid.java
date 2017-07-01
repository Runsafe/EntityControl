package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Sound;
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

		this.world = world;
		goalSelector.a(0, new PathfinderGoalFloat(this));
		setBaby(true);

		/*
		 * Silence default sounds.
		 * v1_9_R2: c
		 * v1_10_R1 and up: setSilent
		 */
		b(false);
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
	 * Interact with a player.
	 * Called when a player right clicks on this entity.
	 * Method name stays the same up to 1.12, argument types differ.
	 * Argument types:
	 * v1_8_R3: EntityHuman
	 * v1_9_R2/v1_10_R1: EntityHuman, EnumHand, ItemStack
	 * v1_11_R1/v1_12_R1: EntityHuman, EnumHand
	 * @param entityhuman Player that right clicked on this entity.
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
	public Sound getInteractSound()
	{
		return null;
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

		if (soundTicks > 0)
			soundTicks--;

		if (player == null || !player.isAlive() || !player.world.worldData.getName().equals(world.getName()) || !CompanionHandler.entityIsSummoned(this))
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
	 * @param sound sound to play.
	 */
	public void playSound(Sound sound)
	{
		if (soundTicks == 0 && sound != null)
		{
			final float SOUND_VOLUME = 1.0F;
			final float SOUND_PITCH = (random.nextFloat() - random.nextFloat()) * 0.2F + 1.5F;
			sound.Play(world.getLocation(locX, locY, locZ), SOUND_VOLUME, SOUND_PITCH);
			soundTicks = 40;
		}
	}

	private IWorld world;
	private int soundTicks = 0;
	private int randomThingTicks = 12000;
	private int randomThingProgress = 0;
	protected EntityPlayer player;
	protected final Random random = new Random();
}
