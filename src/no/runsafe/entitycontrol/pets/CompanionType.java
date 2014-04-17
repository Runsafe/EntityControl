package no.runsafe.entitycontrol.pets;

public enum CompanionType
{
	ZOMBIE("Zombie", ZombieCompanion.class, 54);

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
