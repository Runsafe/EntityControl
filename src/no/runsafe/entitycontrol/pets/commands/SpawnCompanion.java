package no.runsafe.entitycontrol.pets.commands;

import no.runsafe.entitycontrol.pets.CompanionHandler;
import no.runsafe.entitycontrol.pets.CompanionType;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class SpawnCompanion extends PlayerCommand
{
	public SpawnCompanion(CompanionHandler handler)
	{
		super("spawn", "Spawn a companion pet", "runsafe.companions.spawn", new CompanionArgument());
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		handler.spawnCompanion(executor.getLocation(), CompanionType.valueOf(parameters.getRequired("companion")), executor);
		return null;
	}

	private final CompanionHandler handler;
}
