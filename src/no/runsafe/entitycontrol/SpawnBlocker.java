package no.runsafe.entitycontrol;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.event.entity.INaturalSpawn;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;

public class SpawnBlocker implements INaturalSpawn
{
	public SpawnBlocker(IConsole console)
	{
		this.console = console;
	}

	@Override
	public boolean OnNaturalSpawn(RunsafeEntity entity, ILocation location)
	{
		console.logInformation(entity.getClass().getName());
		return true;
	}

	private final IConsole console;
}
