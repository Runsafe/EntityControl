package no.runsafe.entitycontrol.pets;

public class SummonedPet
{
	public SummonedPet(CompanionType type, int entityID)
	{
		this.type = type;
		this.entityID = entityID;
	}

	public CompanionType getType()
	{
		return type;
	}

	public int getEntityID()
	{
		return entityID;
	}

	private final CompanionType type;
	private final int entityID;
}
