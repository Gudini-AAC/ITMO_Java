package commands;

import java.io.*;
import commands.CommandRegestry;
import commands.Command;
import database.Database;
import java.lang.Exception;
import java.lang.Error;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;

/**
* @brief Class that holds a list of all commands and can dispatch between them.
*/
public class CommandRunner {
	
	/**
	* @brief Execute one of the regestred commands.
	* @param args trailing strings that are given with the command.
	* @param database for command to change it.
	* @param reader for command to interact with the user.
	* @param writer for command to interact with the user.
	*/
	public static void runCommand(String[] args, Database database, BufferedReader reader, BufferedWriter writer) throws CommandException, IOException {
		Class commandClass = commandMap.get(args[0]);
		
		try {
			if (commandClass != null) {
				Command command = (Command)commandClass.newInstance();
				command.execute(database, reader, writer, Arrays.copyOfRange(args, 1, args.length));
			} else {
				writer.write("Unknown command. Use \"help\" to list all commands.\n");
			}
		} catch (IllegalAccessException | InstantiationException e) {
			writer.write("Internal error: corrupted command regestry.\n");
		}
		
		writer.flush();
	}
	
	/**
	* @brief Populate the command map with the data from a command regestry.
	*/
	private static Map<String, Class> makeMap() {
		Map<String, Class> map = new HashMap<String, Class>();
		
		try {
			for (int i = 0; i < CommandRegestry.regestry.length; i++)
				map.put(((Command)CommandRegestry.regestry[i].newInstance()).keyString(), CommandRegestry.regestry[i]);
		} catch (Exception e) {
			throw new Error("Internal error: corrupted command regestry.");
		}
		
		return map;
	}
	
	/**
	* @brief commandMap is used to find the command class corresponding to the user input.
	*/
	private static Map<String, Class> commandMap = makeMap();
}
