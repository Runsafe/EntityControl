package no.runsafe.entitycontrol.seat;

import net.minecraft.server.v1_7_R2.EntityPlayer;
import net.minecraft.server.v1_7_R2.MobEffect;
import net.minecraft.server.v1_7_R2.MobEffectList;
import net.minecraft.server.v1_7_R2.World;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.tools.nms.EntityRegister;

public class SeatCommand extends PlayerCommand implements IServerReady
{
	public SeatCommand()
	{
		super("sit", "Sit down!", "runsafe.sit");
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		ILocation location = executor.getLocation();
		World world = ObjectUnwrapper.getMinecraft(executor.getWorld());

		if (location != null && world != null)
		{
			SeatEntity seat = new SeatEntity(world);
			seat.setPosition(location.getX(), location.getY(), location.getZ());
			seat.addEffect(new MobEffect(MobEffectList.INVISIBILITY.id, 864000, 1));
			world.addEntity(seat);

			EntityPlayer player = ObjectUnwrapper.getMinecraft(executor);
			if (player != null)
				player.setPassengerOf(seat);
			else
				seat.dead = true;
		}

		return null;
	}

	@Override
	public void OnServerReady()
	{
		EntityRegister.registerEntity(SeatEntity.class, "Seat", 93);
	}
}
