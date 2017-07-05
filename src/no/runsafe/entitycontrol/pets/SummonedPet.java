package no.runsafe.entitycontrol.pets;

import no.runsafe.framework.api.entity.ILivingEntity;

public class SummonedPet
{
	/**
	 * Constructor for SummonedPet.
	 * @param type Companion pet type.
	 * @param pet the companion pet object.
	 */
	public SummonedPet(CompanionType type, ILivingEntity pet)
	{
		this.type = type;
		this.entityID = pet.getEntityId();
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
	public ILivingEntity getPet()
	{
		return pet;
	}

	private final CompanionType type;
	private final int entityID;
	private final ILivingEntity pet;
}
