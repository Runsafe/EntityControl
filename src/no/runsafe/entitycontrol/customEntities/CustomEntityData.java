package no.runsafe.entitycontrol.customEntities;

import net.minecraft.server.v1_7_R1.World;
import no.runsafe.entitycontrol.customEntities.entities.CustomEntity;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.item.meta.RunsafeLeatherArmor;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.item.meta.RunsafeSkull;

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
			dataMap.put(nodeSplit[0], value);
		}

		if (dataMap.containsKey("name"))
			entity.setCustomName(dataMap.get("name"));

		if (dataMap.containsKey("root"))
			entity.setCanMove(false);

		int colour = -1;

		if (dataMap.containsKey("hex"))
			colour = Integer.valueOf(dataMap.get("hex"), 16);

		equip("hlm", 4, dataMap, entity, colour);
		equip("wep", 0, dataMap, entity, colour);
		equip("cst", 3, dataMap, entity, colour);
		equip("leg", 2, dataMap, entity, colour);
		equip("bts", 1, dataMap, entity, colour);

		if (dataMap.containsKey("head"))
		{
			RunsafeSkull skull = (RunsafeSkull) Item.Decoration.Head.Human.getItem();
			skull.setOwner(dataMap.get("head"));
			entity.setEquipment(4, ObjectUnwrapper.getMinecraft(skull));
		}
	}

	private void equip(String type, int slot, HashMap<String, String> data, CustomEntity entity, int colour)
	{
		if (data.containsKey(type))
		{
			Item object = Item.get(data.get(type));
			if (object != null)
			{
				RunsafeMeta itemStack = object.getItem();

				if (colour > -1 && itemStack instanceof RunsafeLeatherArmor)
					((RunsafeLeatherArmor) itemStack).setColor(colour);

				entity.setEquipment(slot, ObjectUnwrapper.getMinecraft(itemStack));
			}
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
