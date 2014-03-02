package no.runsafe.entitycontrol.customEntities.commands;

import no.runsafe.entitycontrol.customEntities.NPCHandler;
import no.runsafe.entitycontrol.customEntities.NPCType;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.command.argument.TrailingArgument;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;

public class Spawn extends PlayerCommand
{
	public Spawn(NPCHandler handler)
	{
		super("spawn", "Spawn a custom entity", "runsafe.npc.spawn", new RequiredArgument("mobType"), new TrailingArgument("data", false));
		this.handler = handler;
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		NPCType mobType = NPCType.getByName(parameters.get("mobType"));
		if (mobType == null)
			return "&cInvalid mob type!";

		handler.addNPC(executor.getLocation(), mobType, parameters.containsKey("data") ? parameters.get("data") : "");
		return "&eNPC spawned!";
	}

	private final NPCHandler handler;
}
