package no.runsafe.entitycontrol.customEntities;

import net.minecraft.server.v1_7_R1.World;
import no.runsafe.entitycontrol.customEntities.entities.CustomEntity;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Item;

import java.util.HashMap;

public class CustomEntityData
{
	public CustomEntityData(int id, ILocation location, NPCType type, String data)
	{
		this.id = id;
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
			processData(entity);
			return entity;
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private void processData(CustomEntity entity)
	{
		HashMap<String, String> dataMap = new HashMap<String, String>(0);
		String[] parts = data.split(",");
		for (String part : parts)
		{
			String[] nodeSplit = part.split(":");
			String value = nodeSplit.length > 1 ? nodeSplit[1] : null;
			dataMap.put(part, value);
		}

		if (dataMap.containsKey("name"))
			entity.setCustomName(dataMap.get("name"));

		if (dataMap.containsKey("root"))
			entity.setCanMove(false);

		equip("hlm", 4, dataMap, entity);
		equip("wep", 0, dataMap, entity);
		equip("cst", 3, dataMap, entity);
		equip("leg", 2, dataMap, entity);
		equip("bts", 1, dataMap, entity);
	}

	private void equip(String type, int slot, HashMap<String, String> data, CustomEntity entity)
	{
		if (data.containsKey(type))
		{
			Item object = Item.get(data.get(type));
			if (object != null)
				entity.setEquipment(slot, ObjectUnwrapper.getMinecraft(object.getItem()));
		}
	}

	public int getId()
	{
		return id;
	}

	private final int id;
	private final ILocation location;
	private final World world;
	private final NPCType type;
	private final String data;
}
