package no.runsafe.entitycontrol;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.IOutput;
import no.runsafe.framework.api.event.entity.INaturalSpawn;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;
import no.runsafe.framework.minecraft.RunsafeLocation;
import no.runsafe.framework.minecraft.RunsafeWorld;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.entity.RunsafeEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntitySpawn implements INaturalSpawn, IConfigurationChanged
{
	public EntitySpawn(IOutput output)
	{
		this.output = output;
	}

	@Override
	public boolean OnNaturalSpawn(RunsafeEntity entity, RunsafeLocation location)
	{
		this.output.fine("Spawn of %s in world %s", entity, location.getWorld().getName());
		if (entity.getEntityType() instanceof LivingEntity)
		{
			LivingEntity livingEntityType = (LivingEntity) entity.getEntityType();
			RunsafeWorld world = location.getWorld();

			if (this.isBlocked(world, livingEntityType))
			{
				this.output.fine("Prevented spawn of %s in world %s.", livingEntityType.getName(), world.getName());
				return false;
			}
		}
		return true;
	}

	private boolean isBlocked(RunsafeWorld world, LivingEntity livingEntity)
	{
		String worldName = world.getName();

		if (this.preventSpawns.containsKey(worldName))
			if (this.preventSpawns.get(worldName).contains(livingEntity))
				return true;

		if (this.preventSpawns.containsKey("*"))
			if (this.preventSpawns.get("*").contains(livingEntity))
				return true;

		return false;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		preventSpawns.clear();
		boolean hasRemoved = false;
		Map<String, List<String>> map = configuration.getConfigSectionsAsList("preventSpawning");

		for (Map.Entry<String, List<String>> node : map.entrySet())
		{
			String worldName = node.getKey();
			if (!this.preventSpawns.containsKey(worldName))
				this.preventSpawns.put(worldName, new ArrayList<LivingEntity>());

			for (String entityType : node.getValue())
			{
				try
				{
					LivingEntity livingEntityType = LivingEntity.valueOf(entityType);
					if (!this.preventSpawns.get(worldName).contains(livingEntityType))
						this.preventSpawns.get(worldName).add(livingEntityType);

				}
				catch (IllegalArgumentException exception)
				{
					this.output.logError("Invalid entity type %s in config, removing.", entityType);
					map.get("worldName").remove(entityType);
					hasRemoved = true;
				}
			}
		}

		if (hasRemoved)
		{
			configuration.setConfigValue("preventSpawning", map);
			configuration.save();
		}
	}

	private HashMap<String, List<LivingEntity>> preventSpawns = new HashMap<String, List<LivingEntity>>();
	private IOutput output;
}
