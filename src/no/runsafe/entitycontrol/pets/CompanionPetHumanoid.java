package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.*;
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
	public CompanionPetHumanoid(World world)
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
	public void B()
	{
		// Entity base tick
		super.B();

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
			/*
			 * Function names for sound pitch:
			 * v1_7_R3: bf()
			 * v1_8_R3: bC()
			 * v1_9_R2: cf()
			 * v1_10_R1: ci()
			 */
			final float SOUND_VOLUME = 1.0F;
			makeSound(sound, SOUND_VOLUME, bC());
			soundTicks = 40;
		}
	}

	private int soundTicks = 0;
	private int randomThingTicks = 12000;
	private int randomThingProgress = 0;
	protected EntityPlayer player;
	protected final Random random = new Random();
}
