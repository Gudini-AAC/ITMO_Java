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

public class CommandRunner {

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
	
	private static Map<String, Class> commandMap = makeMap();
}
