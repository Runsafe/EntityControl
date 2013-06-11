package no.runsafe.entitycontrol;

import no.runsafe.framework.api.IConfiguration;
import no.runsafe.framework.api.event.plugin.IConfigurationChanged;

public class Options implements IConfigurationChanged
{
	public boolean disableEnderPortalCreation()
	{
		return this.disableEnderPortalCreation;
	}

	public boolean enderDragonDropsEgg()
	{
		return this.enderDragonDropsEgg;
	}

	@Override
	public void OnConfigurationChanged(IConfiguration configuration)
	{
		this.disableEnderPortalCreation = configuration.getConfigValueAsBoolean("disableEnderPortalCreation");
		this.enderDragonDropsEgg = configuration.getConfigValueAsBoolean("enderDragonDropsEgg");
	}

	private boolean disableEnderPortalCreation;
	private boolean enderDragonDropsEgg;
}
