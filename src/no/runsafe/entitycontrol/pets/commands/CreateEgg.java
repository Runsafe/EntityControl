package no.runsafe.entitycontrol.pets.commands;

import no.runsafe.entitycontrol.pets.CompanionType;
import no.runsafe.framework.api.command.argument.IArgumentList;
import no.runsafe.framework.api.command.player.PlayerCommand;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;

public class CreateEgg extends PlayerCommand
{
	public CreateEgg()
	{
		super("egg", "Create a companion egg", "runsafe.companions.egg", new CompanionArgument());
	}

	@Override
	public String OnExecute(IPlayer executor, IArgumentList parameters)
	{
		CompanionType type = CompanionType.valueOf(parameters.get("companion"));
		RunsafeMeta item = type.getSpawnerItem().getItem();
		item.setDisplayName("§2Companion Egg");
		item.addLore("§7Pet: " + type.getTitle());
		item.addLore("§3Right click to summon/dismiss.");
		executor.give(item);
		return null;
	}
}
