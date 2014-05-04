package no.runsafe.entitycontrol;

import net.minecraft.server.v1_7_R2.Entity;
import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.event.entity.INaturalSpawn;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;

import java.util.ArrayList;
import java.util.List;

public class SpawnBlocker implements INaturalSpawn, IConfigurationChanged
{
	@Override
	public boolean OnNaturalSpawn(RunsafeEntity entity, ILocation location)
	{
		Entity rawEntity = ObjectUnwrapper.getMinecraft(entity);
		return !(rawEntity != null && worlds.contains(location.getWorld().getName()));
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		worlds = configuration.getConfigValueAsList("preventNaturalSpawning");
	}

	private List<String> worlds = new ArrayList<String>(0);
}
