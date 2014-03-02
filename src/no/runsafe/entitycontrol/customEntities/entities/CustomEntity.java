package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.EntityCreature;
import net.minecraft.server.v1_7_R1.World;

public abstract class CustomEntity extends EntityCreature
{
	public CustomEntity(World world)
	{
		super(world);
	}
}
