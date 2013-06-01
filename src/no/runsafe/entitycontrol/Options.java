package no.runsafe.entitycontrol;

import no.runsafe.framework.configuration.IConfiguration;
import no.runsafe.framework.event.IConfigurationChanged;

public class Options implements IConfigurationChanged
{
	public boolean disableEnderPortalCreation()
	{
		return this.disableEnderPortalCreation;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.disableEnderPortalCreation = configuration.getConfigValueAsBoolean("disableEnderPortalCreation");
	}

	private boolean disableEnderPortalCreation;
}
