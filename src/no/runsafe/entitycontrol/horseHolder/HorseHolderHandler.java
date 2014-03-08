package no.runsafe.entitycontrol.horseHolder;

import net.minecraft.server.v1_7_R1.*;
import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.entity.IEntity;
import no.runsafe.framework.api.event.player.IPlayerInteractEntityEvent;
import no.runsafe.framework.api.log.IConsole;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import no.runsafe.framework.minecraft.event.player.RunsafePlayerInteractEntityEvent;

import java.io.*;
import java.lang.reflect.Method;
import java.math.BigInteger;

public class HorseHolderHandler implements IPlayerInteractEntityEvent
{
	public HorseHolderHandler(IConsole console)
	{
		this.console = console;
	}

	@Override
	public void OnPlayerInteractEntityEvent(RunsafePlayerInteractEntityEvent event)
	{
		IEntity entity = event.getRightClicked();
		if (entity != null && entity.getEntityType() == LivingEntity.Horse)
			storeHorse((EntityHorse) ObjectUnwrapper.getMinecraft(entity));
	}

	public void storeHorse(EntityHorse horse)
	{
		NBTTagCompound compound = new NBTTagCompound();
		horse.b(compound);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		DataOutputStream dataOutput = new DataOutputStream(outputStream);

		writeTag(dataOutput, compound);
		String data = new BigInteger(1, outputStream.toByteArray()).toString(32);
		console.logInformation(data);
	}

	public void spawnHorse(ILocation location, String data)
	{
		byte[] bytes = new BigInteger(data, 32).toByteArray();

		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		DataInputStream dis = new DataInputStream(bis);

		NBTTagCompound compound = (NBTTagCompound) readTag(dis);
		World world = ObjectUnwrapper.getMinecraft(location.getWorld());
		if (world == null)
			return;

		EntityHorse horse = new EntityHorse(world);
		horse.a(compound);
		horse.setPosition(location.getX(), location.getY(), location.getZ());
		world.addEntity(horse);
	}

	private static void writeTag(DataOutput output, NBTBase tag)
	{
		try
		{
			Method method = NBTCompressedStreamTools.class.getDeclaredMethod("a", NBTBase.class, DataOutput.class);
			method.setAccessible(true);
			method.invoke(null, tag, output);
		}
		catch (Exception e)
		{
			// Welp!
		}
	}

	public static NBTBase readTag(DataInput input)
	{
		try {
			Method method = NBTCompressedStreamTools.class.getDeclaredMethod("a", DataInput.class, int.class);
			method.setAccessible(true);

			return (NBTBase) method.invoke(null, input, 0);
		}
		catch (Exception e)
		{
			// Welp!
		}

		return null;
	}

	private IConsole console;
}
