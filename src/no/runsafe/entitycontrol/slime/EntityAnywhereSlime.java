package no.runsafe.entitycontrol.slime;

import net.minecraft.server.v1_8_R3.*;

public class EntityAnywhereSlime extends EntitySlime
{
	public EntityAnywhereSlime(World world)
	{
		super(world);
	}

	@Override
	public boolean canSpawn()
	{
		return !(world.getWorldData().getType() == WorldType.FLAT && random.nextInt(4) != 1) && (getSize() == 1 || world.difficulty != EnumDifficulty.PEACEFUL) && random.nextInt(10) == 0;
	}
}
