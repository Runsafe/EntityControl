package no.runsafe.entitycontrol.customEntities.entities;

import net.minecraft.server.v1_7_R2.*;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.item.meta.RunsafeLeatherArmor;
import no.runsafe.framework.minecraft.item.meta.RunsafeMeta;
import no.runsafe.framework.minecraft.item.meta.RunsafeSkull;
import org.bukkit.craftbukkit.v1_7_R2.util.UnsafeList;

import java.lang.reflect.Field;
import java.util.HashMap;

public abstract class CustomEntity extends EntitySkeleton
{
	public CustomEntity(World world)
	{
		super(world);

		try
		{
			Field gsa = PathfinderGoalSelector.class.getDeclaredField("b");
			gsa.setAccessible(true);
			gsa.set(this.goalSelector, new UnsafeList());
			gsa.set(this.targetSelector, new UnsafeList());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	protected String t()
	{
		return "none";
	}

	@Override
	protected String aT()
	{
		return "none";
	}

	@Override
	protected String aS()
	{
		return "none";
	}

	@Override
	protected void a(int i, int j, int k, Block block)
	{
		// Do nothing!
	}

	@Override
	public void move(double d0, double d1, double d2)
	{
		if (canMove)
			super.move(d0, d1, d2);
	}

	@Override
	public boolean damageEntity(DamageSource damageSource, float v)
	{
		return !invincible && super.damageEntity(damageSource, v);
	}

	@Override
	public boolean a(EntityHuman entityhuman)
	{
		return false;
	}

	@Override
	protected void dropDeathLoot(boolean flag, int i)
	{
		// Do nothing.
	}

	public void setCanMove(boolean canMove)
	{
		this.canMove = canMove;
	}

	public void setInvincible(boolean invincible)
	{
		this.invincible = invincible;
	}

	@Override
	public EnumMonsterType getMonsterType()
	{
		return EnumMonsterType.UNDEFINED;
	}

	@Override
	public GroupDataEntity a(GroupDataEntity groupdataentity)
	{
		return groupdataentity;
	}

	@Override
	public void a(NBTTagCompound nbttagcompound)
	{
		super.a(nbttagcompound);
		if (nbttagcompound.hasKey("customData"))
			setData(nbttagcompound.getString("customData"));
	}

	@Override
	public void b(NBTTagCompound nbttagcompound)
	{
		super.b(nbttagcompound);
		nbttagcompound.setString("customData", data);
	}

	public void setData(String data)
	{
		this.data = data;
		processData(data);
	}

	private void processData(String data)
	{
		HashMap<String, String> dataMap = new HashMap<String, String>(0);
		String[] parts = data.split(",");
		for (String part : parts)
		{
			String[] nodeSplit = part.split(":");
			String value = nodeSplit.length > 1 ? nodeSplit[1] : null;
			dataMap.put(nodeSplit[0], value);
		}

		if (dataMap.containsKey("name"))
			setCustomName(dataMap.get("name"));

		if (dataMap.containsKey("root"))
			setCanMove(false);
		else
			goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));

		if (dataMap.containsKey("float"))
			goalSelector.a(1, new PathfinderGoalFloat(this));

		if (dataMap.containsKey("flee"))
			goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));

		if (dataMap.containsKey("watch"))
			goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

		if (dataMap.containsKey("atk"))
		{
			goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
			targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
		}

		if (dataMap.containsKey("god"))
			setInvincible(true);

		if (dataMap.containsKey("leap"))
			goalSelector.a(4, new PathfinderGoalLeapAtTarget(this, 3.0F));

		if (dataMap.containsKey("fireproof"))
		{
			try
			{
				Field fireproof = Entity.class.getDeclaredField("fireProof");
				fireproof.setAccessible(true);
				fireproof.set(this, true);
			}
			catch (Exception e)
			{
				// Oh noes!
			}
		}

		int colour = -1;

		if (dataMap.containsKey("hex"))
			colour = Integer.valueOf(dataMap.get("hex"), 16);

		equip("hlm", 4, dataMap, colour);
		equip("wep", 0, dataMap, colour);
		equip("cst", 3, dataMap, colour);
		equip("leg", 2, dataMap, colour);
		equip("bts", 1, dataMap, colour);

		if (dataMap.containsKey("head"))
		{
			RunsafeSkull skull = (RunsafeSkull) no.runsafe.framework.minecraft.Item.Decoration.Head.Human.getItem();
			skull.setOwner(dataMap.get("head"));
			setEquipment(4, ObjectUnwrapper.getMinecraft(skull));
		}
	}

	private void equip(String type, int slot, HashMap<String, String> data, int colour)
	{
		if (data.containsKey(type))
		{
			no.runsafe.framework.minecraft.Item object = no.runsafe.framework.minecraft.Item.get(data.get(type));
			if (object != null)
			{
				RunsafeMeta itemStack = object.getItem();

				if (colour > -1 && itemStack instanceof RunsafeLeatherArmor)
					((RunsafeLeatherArmor) itemStack).setColor(colour);

				setEquipment(slot, ObjectUnwrapper.getMinecraft(itemStack));
			}
		}
	}

	private boolean canMove = true;
	private boolean invincible = false;
	private String data;
}
