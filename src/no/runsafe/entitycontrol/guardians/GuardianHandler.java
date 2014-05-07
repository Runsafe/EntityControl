package no.runsafe.entitycontrol.guardians;

import no.runsafe.framework.api.event.IServerReady;
import no.runsafe.framework.tools.nms.EntityRegister;

public class GuardianHandler implements IServerReady
{
	@Override
	public void OnServerReady()
	{
		EntityRegister.registerEntity(EntityGuardian.class, "Guardian", 99);
	}
}
