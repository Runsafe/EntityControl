package no.runsafe.entitycontrol.guardians;

import net.minecraft.server.v1_7_R3.World;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;

public class SpawnGuardian extends PlayerCommand
{
	public SpawnGuardian()
	{
		super("spawnguardian", "Spawns a guardian", "runsafe.guardians.spawn", new RequiredArgument("name"));
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		World rawWorld = ObjectUnwrapper.getMinecraft(executor.getWorld());
		ILocation position = executor.getLocation();
		if (rawWorld != null && position != null)
		{
			EntityGuardian guardian = new EntityGuardian(rawWorld);
			guardian.setPosition(position.getX(), position.getY(), position.getZ());
			rawWorld.addEntity(guardian);
			guardian.setCustomName(parameters.get("name"));
		}
		return null;
	}
}
