package no.runsafe.entitycontrol;

import no.runsafe.entitycontrol.customEntities.*;
import no.runsafe.entitycontrol.customEntities.commands.Spawn;
import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.features.Commands;
import no.runsafe.framework.features.Database;
import no.runsafe.framework.features.Events;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void pluginSetup()
	{
		addComponent(Commands.class);
		addComponent(Events.class);
		addComponent(Database.class);

		this.addComponent(Options.class);
		this.addComponent(EntityPortalCreation.class);
		this.addComponent(EntityDeath.class);
		//this.addComponent(EntitySpawn.class);

		// Custom entity stuff
		addComponent(NPCRepository.class);
		addComponent(NPCHandler.class);
		addComponent(NPCSpawner.class);

		Command npcCommand = new Command("npc", "NPC related commands", null);
		addComponent(npcCommand);

		npcCommand.addSubCommand(getInstance(Spawn.class));
	}
}
