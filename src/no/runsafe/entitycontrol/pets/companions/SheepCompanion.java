package no.runsafe.entitycontrol.pets.companions;

import net.minecraft.server.v1_8_R3.*;
import no.runsafe.entitycontrol.pets.CompanionHandler;
import no.runsafe.entitycontrol.pets.ICompanionPet;
import no.runsafe.entitycontrol.pets.PathfinderGoalFollowPlayer;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Sound;
import org.bukkit.craftbukkit.v1_8_R3.util.UnsafeList;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import static net.minecraft.server.v1_8_R3.EnumColor.fromInvColorIndex;

public class SheepCompanion extends EntitySheep implements ICompanionPet
{
	public SheepCompanion(IWorld world)
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
		goalSelector.a(1, new PathfinderGoalFollowPlayer(player, this));
	}
}
