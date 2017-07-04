package no.runsafe.entitycontrol.pets;

import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.minecraft.Sound;

public interface ICompanionPet
{
	void setFollowingPlayer(IPlayer player);
	void setLocation(double x, double y, double z, float f, float f1);
}
