package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.entitycontrol.pets.CompanionHandler;
import no.runsafe.entitycontrol.pets.ICompanionPet;
import no.runsafe.entitycontrol.pets.PathfinderGoalFollowPlayer;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;
import java.lang.reflect.Field;
import static net.minecraft.server.v1_8_R3.EnumColor.fromInvColorIndex;

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

	/*
	* Play idle sound
	* Names of this function in different spigot versions:
	* v1_7_R3: t
	* v1_8_R3: z
	* v1_9_R2: G, returns SoundEffect
	* v1_10_R1: G, returns SoundEffect
	 */
	@Override
	protected String z()
	{
		return "none";
	}

	/*
	* Play death sound
	* Names of this function in various spigot versions:
	* v1_7_R3: aT
	* v1_8_R3: bp
	* v1_9_R2: bT, returns SoundEffect
	* v1_10_R1: bW, returns SoundEffect
	 */
	@Override
	protected String bp()
	{
		return "none";
	}

	/*
	* Play hurt sound
	* Names of this function in various spigot versions:
	* v1_7_R3: aS
	* v1_8_R3: bo
	* v1_9_R2: bS, returns SoundEffect
	* v1_10_R1: bV, returns SoundEffect
	 */
	@Override
	protected String bo()
	{
		return "none";
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

		if (colourChangeTicks == 0)
		{
			colourChangeTicks = 6000;
			setColor(fromInvColorIndex(random.nextInt(15) + 1));
		}
		colourChangeTicks--;
	}

	public void playSound(String sound)
	{
		if (soundTicks == 0)
		{
			/*
			* Function names:
			* v1_7_R3: be(), bf()
			* v1_8_R3: bB(), bC()
			* v1_9_R2: ce(), cf()
			* v1_10_R1: ch(), ci()
			*/
			makeSound(sound, bB(), bC());
			soundTicks = 40;
		}
	}

	private int soundTicks = 0;
	private int colourChangeTicks = 6000;
	protected EntityPlayer player;
}
