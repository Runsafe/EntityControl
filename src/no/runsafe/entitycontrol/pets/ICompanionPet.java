package no.runsafe.entitycontrol.pets;

import no.runsafe.framework.api.player.IPlayer;

public interface ICompanionPet
{
	void setFollowingPlayer(IPlayer player);
	void setBaby(boolean flag);
	void setLocation(double x, double y, double z, float f, float f1);
}
