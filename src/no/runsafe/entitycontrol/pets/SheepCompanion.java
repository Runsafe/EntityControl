package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_7_R2.EntityHuman;
import net.minecraft.server.v1_7_R2.World;

import java.util.Random;

public class SheepCompanion extends CompanionPetAnimal
{
	public SheepCompanion(World world)
	{
		super(world);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		playSound("mob.sheep.say");
		return false;
	}

	@Override
	public void B()
	{
		super.B(); // Entiy base tick

		if (colourChangeTicks == 0)
		{
			colourChangeTicks = 12000;
			int randomColour = colours[random.nextInt(colours.length)];
			datawatcher.watch(16, datawatcher.getByte(16) & 240 | randomColour & 15);
		}
		colourChangeTicks--;
	}

	//private int colourChangeTicks = 12000;
	private int colourChangeTicks = 1000;
	private final Random random = new Random();
	private int[] colours = {0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF};
}
