package no.runsafe.entitycontrol.pets;

import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.PathfinderGoalFloat;
import no.runsafe.framework.api.entity.IAgeable;
import no.runsafe.framework.api.entity.ILivingEntity;
import no.runsafe.framework.api.entity.ISlime;
import no.runsafe.framework.api.entity.animals.IOcelot;
import no.runsafe.framework.api.entity.monsters.IZombie;
import no.runsafe.framework.api.entity.villagers.IVillager;
import no.runsafe.framework.api.player.IPlayer;
import no.runsafe.framework.internal.wrapper.ObjectUnwrapper;
import no.runsafe.framework.minecraft.Item;
import no.runsafe.framework.minecraft.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Villager;

import javax.annotation.Nonnull;

public enum CompanionType
{
	ZOMBIE(LivingEntity.Zombie, "Brainy (Zombie)", Item.Miscellaneous.MonsterEgg.Zombie),
	CHICKEN(LivingEntity.Chicken, "Cluckers (Chicken)", Item.Miscellaneous.MonsterEgg.Chicken),
	COW(LivingEntity.Cow, "Moops (Cow)", Item.Miscellaneous.MonsterEgg.Cow),
	MOOSHROOM(LivingEntity.MushroomCow, "Mushy (Mooshroom)", Item.Miscellaneous.MonsterEgg.Mooshroom),
	BACON(LivingEntity.Pig, "Oinkers (Pig)", Item.Miscellaneous.MonsterEgg.Pig),
	PIG_ZOMBIE(LivingEntity.PigZombie, "Chomp (Pig Zombie)", Item.Miscellaneous.MonsterEgg.ZombiePigman),
	SHEEP(LivingEntity.Sheep, "Bart (Sheep)", Item.Miscellaneous.MonsterEgg.Sheep),
	WOLF(LivingEntity.Wolf, "Whiskers (Wolf)", Item.Miscellaneous.MonsterEgg.Wolf),
	SLIME(LivingEntity.Slime, "Slippy (Slime)", Item.Miscellaneous.MonsterEgg.Slime),
	MAGMA_CUBE(LivingEntity.LavaSlime, "Flames (Magma Cube)", Item.Miscellaneous.MonsterEgg.MagmaCube),
	LIBRARIAN( LivingEntity.Villager, "Vinny (Villager - Librarian)", Item.Miscellaneous.MonsterEgg.Villager),
	FARMER(LivingEntity.Villager, "Chuck (Villager - Farmer)", Item.Miscellaneous.MonsterEgg.Villager),
	PRIEST(LivingEntity.Villager, "Davy (Villager - Priest)", Item.Miscellaneous.MonsterEgg.Villager),
	BLACKSMITH(LivingEntity.Villager, "Manny (Villager - Blacksmith)", Item.Miscellaneous.MonsterEgg.Villager),
	BUTCHER(LivingEntity.Villager, "Vug (Villager - Butcher)", Item.Miscellaneous.MonsterEgg.Villager),
	OCELOT(LivingEntity.Ocelot, "Nibbles (Ocelot)", Item.Miscellaneous.MonsterEgg.Ocelot),
	SILVERFISH(LivingEntity.Silverfish, "Yoppers (Silverfish)", Item.Miscellaneous.MonsterEgg.Silverfish),
	BAT(LivingEntity.Bat, "Flappy (Bat)", Item.Miscellaneous.MonsterEgg.Bat),
	FLUFFERS(LivingEntity.Ocelot, "Fluffers (Cat)", Item.Miscellaneous.MonsterEgg.Ocelot),
	MITTENS(LivingEntity.Ocelot, "Mittens (Cat)", Item.Miscellaneous.MonsterEgg.Ocelot),
	MURPS(LivingEntity.Ocelot, "Murps (Cat)", Item.Miscellaneous.MonsterEgg.Ocelot);

	private CompanionType(LivingEntity entityType, String title, Item spawnerItem)
	{
		this.entityType = entityType;
		this.title = title;
		this.spawnerItem = spawnerItem;
	}

	public String getTitle()
	{
		return this.title;
	}

	public Item getSpawnerItem()
	{
		return spawnerItem;
	}

	public ILivingEntity spawnCompanion(@Nonnull IPlayer owner)
	{
		// Spawn companion.
		ILivingEntity pet = ((ILivingEntity) entityType.spawn(owner.getLocation()));

		// Set new pathfinding.
		pet.stopPathfinding();
		pet.setNewPathfindingGoal(0, new PathfinderGoalFloat((EntityInsentient) ObjectUnwrapper.getMinecraft(pet)));
		pet.setNewPathfindingGoal(1, new PathfinderGoalFollowPlayer(owner, pet));

		// Make companion a baby if possible.
		if (pet instanceof IAgeable)
			((IAgeable) pet).setBaby();
		else if (pet instanceof IZombie)
			((IZombie) pet).setBaby(true);

		// Silence default sounds.
		pet.setSilent(false);

		pet.setPersistance(false);

		// Do companion-specific things.
		switch(this)
		{
			case SLIME: ((ISlime) pet).setSize(1); break;
			case MAGMA_CUBE: ((ISlime) pet).setSize(1); break;
			case LIBRARIAN: ((IVillager) pet).setProfession(Villager.Profession.LIBRARIAN); break;
			case FARMER: ((IVillager) pet).setProfession(Villager.Profession.FARMER); break;
			case PRIEST: ((IVillager) pet).setProfession(Villager.Profession.PRIEST); break;
			case BLACKSMITH: ((IVillager) pet).setProfession(Villager.Profession.BLACKSMITH); break;
			case BUTCHER: ((IVillager) pet).setProfession(Villager.Profession.BUTCHER); break;
			case FLUFFERS: ((IOcelot) pet).setCatType(Ocelot.Type.BLACK_CAT); break;
			case MITTENS: ((IOcelot) pet).setCatType(Ocelot.Type.SIAMESE_CAT); break;
			case MURPS: ((IOcelot) pet).setCatType(Ocelot.Type.RED_CAT); break;
		}

		return pet;
	}

	private final LivingEntity entityType;
	private final String title;
	private final Item spawnerItem;
}
