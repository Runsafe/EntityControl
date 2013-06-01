package no.runsafe.entitycontrol;

import no.runsafe.framework.RunsafeConfigurablePlugin;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		this.addComponent(Options.class);
		this.addComponent(EntityPortalCreation.class);
	}
}
