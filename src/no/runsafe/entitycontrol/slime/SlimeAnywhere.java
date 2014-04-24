package no.runsafe.entitycontrol.slime;

import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.tools.nms.EntityRegister;

public class SlimeAnywhere implements IServerReady
{
	@Override
	public void OnServerReady()
	{
		EntityRegister.registerOverrideEntity(EntityAnywhereSlime.class, "Slime", 55);
	}
}
