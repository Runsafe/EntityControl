package no.runsafe.entitycontrol.pets;

public class SummonedPet
{
	/**
	 * Constructor for SummonedPet.
	 * @param type Companion pet type.
	 * @param entityID ID for the companion pet.
	 */
	public SummonedPet(CompanionType type, int entityID)
	{
		this.type = type;
		this.entityID = entityID;
	}

	/**
	 * Gets the companion pet's type.
	 * @return Companion pet's type.
	 */
	public CompanionType getType()
	{
		return type;
	}

	/**
	 * Gets the companion pet's entity ID.
	 * @return Object's entity id.
	 */
	public int getEntityID()
	{
		return entityID;
	}

	private final CompanionType type;
	private final int entityID;
}
