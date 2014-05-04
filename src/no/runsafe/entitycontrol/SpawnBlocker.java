package no.runsafe.entitycontrol;

import net.minecraft.server.v1_7_R2.Entity;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.event.entity.INaturalSpawn;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
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
		Entity rawEntity = ObjectUnwrapper.getMinecraft(entity);

		if (rawEntity != null)
			console.logInformation(rawEntity.getClass().getName());

		return true;
	}

	private final IConsole console;
}
