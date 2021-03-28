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
	* @param database For command to change it.
	* @param args     Trailing strings that are given with the command.
	* @param context  Context of a running command call stack.
	* @throws CommandException If command fails.
	* @throws IOException If one of the user io streams fails.
	*/
	public static void runCommand(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args[0].length() == 0) return;
		
		Class commandClass = commandMap.get(args[0]);
		
		try {
			if (commandClass != null) {
				Command command = (Command)commandClass.newInstance();
				command.execute(database, Arrays.copyOfRange(args, 1, args.length), context);
			} else {
				String message = String.format(
					"Unknown command \"%s\". Use \"help\" to list all commands.", args[0]);
				throw new CommandException(message);
			}
		} catch (IllegalAccessException | InstantiationException e) {
			throw new Error("Internal error: corrupted command regestry.");
		}
	}
	
	/**
	* @brief  Populate the command map with the data from a command regestry.
	* @return Command name to command class map.
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
