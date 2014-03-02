package no.runsafe.entitycontrol.customEntities;

import no.runsafe.framework.api.ILocation;
import no.runsafe.framework.api.database.*;
import no.runsafe.framework.api.log.IConsole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NPCRepository extends Repository
{
	public NPCRepository(IDatabase database, IConsole console)
	{
		this.console = console;
		this.database = database;
	}

	public HashMap<String, List<CustomEntityData>> getNPCList()
	{
		HashMap<String, List<CustomEntityData>> list = new HashMap<String, List<CustomEntityData>>(0);

		// Grab all NPC info from the database
		for (IRow row : database.query("SELECT * FROM `npcs`"))
		{
			int id = row.Integer("ID");
			NPCType type = NPCType.getByID(row.Integer("mobType")); // Grab the type of mob

			// If our type is null, remove the NPC because we can't spawn it
			if (type == null)
			{
				console.logWarning("Invalid NPC found in database with ID %s, removing.", id);
				database.execute("DELETE FROM `npcs` WHERE `ID` = ?", id);
				continue; // Skip to the next loop iteration
			}

			String worldName = row.String("world"); // Name of the world
			if (!list.containsKey(worldName)) // If we don't have a container for the world, make one
				list.put(worldName, new ArrayList<CustomEntityData>(1));

			// Add the entity data to our list
			list.get(worldName).add(new CustomEntityData(id, row.Location(), type, row.String("data")));
		}

		return list;
	}

	public int addNPC(ILocation location, NPCType type, String data)
	{
		ITransaction transaction  = database.isolate();

		transaction.execute(
				"INSERT INTO `npcs` (world, x, y, z, yaw, pitch, mobType, data) VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
				location.getWorld().getName(),
				location.getX(),
				location.getY(),
				location.getZ(),
				location.getYaw(),
				location.getPitch(),
				type.getEntityID(),
				data
		);

		Integer newID = transaction.queryInteger("SELECT LAST_INSERT_ID()");
		transaction.Commit();
		return newID == null ? 0 : newID;
	}

	@Override
	public String getTableName()
	{
		return "npcs";
	}

	@Override
	public ISchemaUpdate getSchemaUpdateQueries()
	{
		ISchemaUpdate update = new SchemaUpdate();

		update.addQueries(
			"CREATE TABLE `npcs` (" +
				"`ID` INT(10) UNSIGNED NOT NULL," +
				"`world` VARCHAR(50) NOT NULL," +
				"`x` DOUBLE NOT NULL," +
				"`y` DOUBLE NOT NULL," +
				"`z` DOUBLE NOT NULL," +
				"`yaw` FLOAT NOT NULL," +
				"`pitch` FLOAT NOT NULL," +
				"`mobType` TINYINT UNSIGNED NOT NULL," +
				"`data` LONGTEXT NOT NULL," +
				"PRIMARY KEY (`ID`)" +
			")"
		);
		return update;
	}

	private final IConsole console;
}
