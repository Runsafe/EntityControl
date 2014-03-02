package no.runsafe.entitycontrol.customEntities;

import no.runsafe.entitycontrol.customEntities.entities.*;

public enum NPCType
{
	IRON_GOLEM(CustomIronGolem.class, 99),
	WOLF(CustomWolf.class, 95),
	MOOSHROOM(CustomMooshroom.class, 96),
	SNOW_GOLEM(CustomSnowGolem.class, 97),
	OCELOT(CustomOcelot.class, 98),
	PIG(CustomPig.class, 90),
	COW(CustomCow.class, 92),
	SHEEP(CustomSheep.class, 91),
	WITCH(CustomWitch.class, 66),
	SILVERFISH(CustomSilverfish.class, 60),
	SKELETON(CustomSkeleton.class, 51),
	CREEPER(CustomCreeper.class, 50),
	SPIDER(CustomSpider.class, 52),
	ZOMBIE_PIGMAN(CustomZombiePigman.class, 57);

	private NPCType(Class<? extends CustomEntity> type, int id)
	{
		entityID = id;
		simpleName = name().toLowerCase().replaceAll("_", "");
		mobType = type;
	}

	public int getEntityID()
	{
		return entityID;
	}

	public String getSimpleName()
	{
		return simpleName;
	}

	public Class<? extends CustomEntity> getMobType()
	{
		return mobType;
	}

	public static NPCType getByID(int id)
	{
		for (NPCType type : values())
			if (type.getEntityID() == id)
				return type;

		return null;
	}

	public static NPCType getByName(String name)
	{
		name = name.toLowerCase().replaceAll("_", "");

		for (NPCType type : values())
			if (type.getSimpleName().equals(name))
				return type;

		return null;
	}

	private final int entityID;
	private final String simpleName;
	private final Class<? extends CustomEntity> mobType;
}
