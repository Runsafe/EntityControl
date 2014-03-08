package no.runsafe.entitycontrol.horseHolder;

import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class SpawnHorseFromString extends PlayerCommand
{
	public SpawnHorseFromString(HorseHolderHandler handler)
	{
		super("shfs", "Spawn horse from string, yeah", "runsafe.spawn.horse", new TrailingArgument("data", true));
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		handler.spawnHorse(executor.getLocation(), parameters.get("data"));
		return "&eSpawned!";
	}

	private final HorseHolderHandler handler;
}
