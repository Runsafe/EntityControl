package no.runsafe.entitycontrol.customEntities;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NPCHandler implements IConfigurationChanged
{
	public NPCHandler(NPCRepository repository)
	{
		this.repository = repository;
	}

	public List<CustomEntityData> getNPCListForWorld(IWorld world)
	{
		String worldName = world.getName();
		if (entities.containsKey(worldName))
			return entities.get(worldName);

		return Collections.emptyList();
	}

	public void addNPC(ILocation location, NPCType type, String data)
	{
		int newID = repository.addNPC(location, type, data);
		if (newID > 0)
		{
			String worldName = location.getWorld().getName();
			if (!entities.containsKey(worldName))
				entities.put(worldName, new ArrayList<CustomEntityData>(1));

			CustomEntityData entity = new CustomEntityData(newID, location, type, data);
			entities.get(worldName).add(entity);

			if (!location.getChunk().isUnloaded())
				entity.spawnEntity();
		}
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		entities = repository.getNPCList(); // Load NPC list from database
	}

	private final NPCRepository repository;
	private HashMap<String, List<CustomEntityData>> entities = new HashMap<String, List<CustomEntityData>>(0);
}
