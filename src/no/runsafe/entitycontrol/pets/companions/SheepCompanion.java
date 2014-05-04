package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_7_R2.*;
import no.runsafe.entitycontrol.pets.CompanionHandler;
import no.runsafe.entitycontrol.pets.ICompanionPet;
import no.runsafe.entitycontrol.pets.PathfinderGoalFollowPlayer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.craftbukkit.v1_7_R2.util.UnsafeList;

import java.lang.reflect.Field;

public class SheepCompanion extends EntitySheep implements ICompanionPet
{
	public SheepCompanion(World world)
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
		playSound("mob.sheep.say");
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

		if (isAlive())
			setAge(-1000);

		if (player == null || !player.isAlive() || !player.world.worldData.getName().equals(world.worldData.getName()) || !CompanionHandler.entityIsSummoned(this))
			dead = true;

		if (colourChangeTicks == 0)
		{
			colourChangeTicks = 6000;
			setColor(random.nextInt(15) + 1);
		}
		colourChangeTicks--;
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
	private int colourChangeTicks = 6000;
	protected EntityPlayer player;
}