package no.runsafe.entitycontrol.pets.commands;

import com.google.common.collect.ImmutableList;
import no.runsafe.entitycontrol.pets.CompanionType;
import no.runsafe.framework.api.command.ICommandExecutor;
import no.runsafe.framework.api.command.argument.ITabComplete;
import no.runsafe.framework.api.command.argument.IValueExpander;
import no.runsafe.framework.api.command.argument.RequiredArgument;
import no.runsafe.framework.api.player.IPlayer;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CompanionArgument extends RequiredArgument implements ITabComplete, IValueExpander
{
	public CompanionArgument()
	{
		super("companion");
	}

	@Override
	public List<String> getAlternatives(IPlayer executor, String partial)
	{
		List<String> options = new ArrayList<>();

		for (CompanionType type : CompanionType.values())
			options.add(type.name());

		if (partial == null || partial.isEmpty())
			return ImmutableList.copyOf(options);

		String match = partial.toLowerCase();
		List<String> alternatives = new ArrayList<>(options.size());

		for (String option : options)
			if (option.toLowerCase().startsWith(match))
				alternatives.add(option);

		return alternatives;
	}

	@Nullable
	@Override
	public String expand(ICommandExecutor context, @Nullable String value)
	{
		List<String> options = getAlternatives((IPlayer) context, value);
		return options.isEmpty() || options.size() > 1 ? null : options.get(0);
	}
}
