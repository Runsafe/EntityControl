package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.*;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.craftbukkit.v1_7_R2.util.UnsafeList;

import java.lang.reflect.Field;
import java.util.Random;

public class CompanionPetHumanoid extends EntityZombie implements ICompanionPet
{
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

		setBaby(true);
	}

	public void setFollowingPlayer(IPlayer player)
	{
		this.player = ObjectUnwrapper.getMinecraft(player);
		goalSelector.a(1, new PathfinderGoalFollowPlayer(this.player, this, 1.0D, 2F, 2F));
	}

	@Override
	protected String t()
	{
		return "none"; // Idle sound.
	}

	@Override
	protected String aT()
	{
		return "none"; // Hurt sound.
	}

	@Override
	protected String aS()
	{
		return "none"; // Death sound.
	}

	@Override
	public boolean damageEntity(DamageSource damagesource, float f)
	{
		return false;
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		// Interact with player.
		return false;
	}

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

		if (player == null || !player.isAlive() || !player.world.worldData.getName().equals(world.worldData.getName()))
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

	public void playSound(String sound)
	{
		if (soundTicks == 0)
		{
			makeSound(sound, be(), bf());
			soundTicks = 40;
		}
	}

	private int soundTicks = 0;
	private int randomThingTicks = 12000;
	private int randomThingProgress = 0;
	protected EntityPlayer player;
	protected final Random random = new Random();
}
