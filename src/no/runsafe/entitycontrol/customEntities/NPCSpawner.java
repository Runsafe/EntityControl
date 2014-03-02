package no.runsafe.entitycontrol.customEntities;

import no.runsafe.framework.api.IWorld;
import no.runsafe.framework.api.chunk.IChunk;
import no.runsafe.framework.api.event.world.IChunkLoad;

import java.util.HashMap;
import java.util.List;

public class NPCSpawner implements IChunkLoad
{
	public NPCSpawner(NPCHandler handler)
	{
		this.handler = handler;
	}

	@Override
	public void OnChunkLoad(IChunk chunk)
	{
		IWorld world = chunk.getWorld();
		List<CustomEntityData> list = handler.getNPCListForWorld(world);
		for (CustomEntityData data : list)
		{
			int entityID = data.getId();
			if (entityMap.containsKey(entityID))
			{
				int spawnedID = entityMap.get(entityID);
				if (world.getEntityById(spawnedID) != null)
					continue;
				else
					entityMap.remove(entityID);
			}
			entityMap.put(entityID, data.spawnEntity().getId());
		}
	}

	private NPCHandler handler;
	private final HashMap<Integer, Integer> entityMap = new HashMap<Integer, Integer>(0);
}
