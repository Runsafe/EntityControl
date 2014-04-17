package no.runsafe.entitycontrol;

import no.runsafe.framework.api.command.Command;
import no.runsafe.framework.api.command.argument.IArgument;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SecondaryCommand extends Command
{
	/**
	 * Defines the command
	 *
	 * @param commandName The name of the command. For top level commands, this must be as defined in plugin.yml
	 * @param description A short descriptive text of what the command does
	 * @param permission  A permission String that a player must have to run the command or null to allow anyone to run it
	 * @param arguments   Optional list of required command parameters
	 */
	public SecondaryCommand(@Nonnull String commandName, @Nonnull String description, @Nullable String permission, IArgument... arguments)
	{
		super(commandName, description, permission, arguments);
	}
}
