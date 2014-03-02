package no.runsafe.entitycontrol.customEntities;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.event.plugin.IPluginEnabled;
import no.runsafe.framework.tools.nms.EntityRegister;

public class NPCHandler implements IPluginEnabled
{
	public void addNPC(ILocation location, NPCType type, String data)
	{
		CustomEntityData entity = new CustomEntityData(location, type, data);

		if (!location.getChunk().isUnloaded())
				entity.spawnEntity();
	}

	@Override
	public void OnPluginEnabled()
	{
		// Register entity types
		for (NPCType type : NPCType.values())
			EntityRegister.registerEntity(type.getMobType(), "custom" + type.getSimpleName(), type.getEntityID());
	}
}
