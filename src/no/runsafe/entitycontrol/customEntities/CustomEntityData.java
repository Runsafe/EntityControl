package no.runsafe.entitycontrol.customEntities;

import net.minecraft.server.v1_7_R1.*;
import no.runsafe.entitycontrol.customEntities.entities.CustomEntity;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;

public class CustomEntityData
{
	public CustomEntityData(ILocation location, NPCType type, String data)
	{
		this.location = location;
		this.world = ObjectUnwrapper.getMinecraft(location.getWorld());
		this.type = type;
		this.data = data;
	}

	public CustomEntity spawnEntity()
	{
		try
		{
			CustomEntity entity = type.getMobType().getConstructor(World.class).newInstance(world);
			entity.setPosition(location.getX(), location.getY(), location.getZ());
			world.addEntity(entity);
			entity.setData(data);
			return entity;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private final ILocation location;
	private final World world;
	private final NPCType type;
	private final String data;
}
