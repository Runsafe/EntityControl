package no.runsafe.entitycontrol;

import no.runsafe.entitycontrol.entityTeleporting.MountedHorseTeleporter;
import no.runsafe.entitycontrol.pets.CompanionHandler;
import no.runsafe.entitycontrol.pets.commands.CreateEgg;
import no.runsafe.entitycontrol.pets.commands.SpawnCompanion;
import no.runsafe.entitycontrol.slime.SlimeAnywhere;
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

		addComponent(MountedHorseTeleporter.class);
		addComponent(HorseSugar.class);

		// Companions
		addComponent(CompanionHandler.class);

		Command companionCommand = new Command("companion", "Companion related commands", null);
		addComponent(companionCommand);
		companionCommand.addSubCommand(getInstance(SpawnCompanion.class));
		companionCommand.addSubCommand(getInstance(CreateEgg.class));

		addComponent(SlimeAnywhere.class);

		addComponent(SpawnBlocker.class);
	}
}
