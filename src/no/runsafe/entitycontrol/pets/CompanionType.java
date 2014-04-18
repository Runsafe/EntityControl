package no.runsafe.entitycontrol.pets;

import no.runsafe.framework.minecraft.Item;

public enum CompanionType
{
	ZOMBIE("Zombie", ZombieCompanion.class, 54, "Brainy (Zombie)", Item.Miscellaneous.MonsterEgg.Zombie),
	CHICKEN("Chicken", ChickenCompanion.class, 93, "Cluckers (Chicken)", Item.Miscellaneous.MonsterEgg.Chicken),
	COW("Cow", CowCompanion.class, 92, "Moops (Cow)", Item.Miscellaneous.MonsterEgg.Cow),
	MOOSHROOM("Mooshroom", MooshroomCompanion.class, 96, "Mushy (Mooshroom)", Item.Miscellaneous.MonsterEgg.Mooshroom),
	BACON("Pig", PigCompanion.class, 90, "Oinkers (Pig)", Item.Miscellaneous.MonsterEgg.Pig),
	PIG_ZOMBIE("PigZombie", PigZombieCompanion.class, 57, "Chomp (Pig Zombie)", Item.Miscellaneous.MonsterEgg.ZombiePigman),
	SHEEP("Sheep", SheepCompanion.class, 91, "Bart (Sheep)", Item.Miscellaneous.MonsterEgg.Sheep),
	WOLF("Wolf", WolfCompanion.class, 95, "Whiskers (Wolf)", Item.Miscellaneous.MonsterEgg.Wolf),
	SLIME("Slime", SlimeCompanion.class, 55, "Slippy (Slime)", Item.Miscellaneous.MonsterEgg.Slime),
	MAGMA_CUBE("MagmaCube", MagmaCubeCompanion.class, 62, "Flames (Magma Cube)", Item.Miscellaneous.MonsterEgg.MagmaCube),
	LIBRARIAN("Librarian", LibrarianCompanion.class, 120, "Vinny (Villager - Librarian)", Item.Miscellaneous.MonsterEgg.Villager),
	FARMER("Farmer", FarmerCompanion.class, 120, "Chuck (Villager - Farmer)", Item.Miscellaneous.MonsterEgg.Villager),
	PRIEST("Priest", PriestCompanion.class, 120, "Davy (Villager - Priest)", Item.Miscellaneous.MonsterEgg.Villager),
	BLACKSMITH("Blacksmith", BlacksmithCompanion.class, 120, "Manny (Villager - Blacksmith)", Item.Miscellaneous.MonsterEgg.Villager),
	BUTCHER("Butcher", ButcherCompanion.class, 120, "Vug (Villager - Butcher)", Item.Miscellaneous.MonsterEgg.Villager),
	OCELOT("Ocelot", OcelotCompanion.class, 98, "Nibbles (Ocelot)", Item.Miscellaneous.MonsterEgg.Ocelot),
	SILVERFISH("Silverfish", SilverfishCompanion.class, 60, "Yoppers (Silverfish)", Item.Miscellaneous.MonsterEgg.Silverfish);

	private CompanionType(String name, Class entityClass, int id, String title, Item spawnerItem)
	{
		this.name = name;
		this.entityClass = entityClass;
		this.id = id;
		this.title = title;
		this.spawnerItem = spawnerItem;
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

	public String getTitle()
	{
		return this.title;
	}

	public Item getSpawnerItem()
	{
		return spawnerItem;
	}

	private final String name;
	private final Class entityClass;
	private final int id;
	private final String title;
	private final Item spawnerItem;
}
