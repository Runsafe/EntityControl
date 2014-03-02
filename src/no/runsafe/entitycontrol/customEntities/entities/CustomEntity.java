package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R1.EntityInsentient;
import net.minecraft.server.v1_7_R1.World;

public abstract class CustomEntity extends EntityInsentient
{
	public CustomEntity(World world)
	{
		super(world);
	}
}
