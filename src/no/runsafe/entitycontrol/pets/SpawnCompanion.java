package no.runsafe.entitycontrol.pets;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class SpawnCompanion extends PlayerCommand
{
	public SpawnCompanion(CompanionHandler handler)
	{
		super("spawn", "Spawn a companion pet", "runsafe.companions.spawn");
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		handler.spawnCompanion(executor.getLocation());
		return null;
	}

	private final CompanionHandler handler;
}
