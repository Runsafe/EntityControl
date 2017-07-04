package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.EntityInsentient;

public class SummonedPet
{
	/**
	 * Constructor for SummonedPet.
	 * @param type Companion pet type.
	 * @param entityID ID for the companion pet.
	 */
	public SummonedPet(CompanionType type, int entityID, EntityInsentient pet)
	{
		this.type = type;
		this.entityID = entityID;
		this.pet = pet;
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

	/**
	 * @return The pet's object.
	 */
	public EntityInsentient getPet()
	{
		return pet;
	}

	private final CompanionType type;
	private final int entityID;
	private final EntityInsentient pet;
}
