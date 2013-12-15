package no.runsafe.entitycontrol;

import no.runsafe.framework.RunsafeConfigurablePlugin;
import no.runsafe.framework.features.Events;

public class Plugin extends RunsafeConfigurablePlugin
{
	@Override
	protected void PluginSetup()
	{
		addComponent(Events.class);

		this.addComponent(Options.class);
		this.addComponent(EntityPortalCreation.class);
		this.addComponent(EntityDeath.class);
		this.addComponent(EntitySpawn.class);
	}
}
