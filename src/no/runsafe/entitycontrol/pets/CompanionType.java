package no.runsafe.entitycontrol.pets;

public enum CompanionType
{
	ZOMBIE("Zombie", ZombieCompanion.class, 54),
	CHICKEN("Chicken", ChickenCompanion.class, 93),
	COW("Cow", CowCompanion.class, 92),
	MOOSHROOM("Mooshroom", MooshroomCompanion.class, 96),
	PIG("Pig", PigCompanion.class, 90),
	PIG_ZOMBIE("PigZombie", PigZombieCompanion.class, 57),
	SHEEP("Sheep", SheepCompanion.class, 91),
	WOLF("Wolf", WolfCompanion.class, 95);

	private CompanionType(String name, Class entityClass, int id)
	{
		this.name = name;
		this.entityClass = entityClass;
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public Class getEntityClass()
	{
		return this.entityClass;
	}

	public int getId()
	{
		return id;
	}

	private final String name;
	private final Class entityClass;
	private final int id;
}
