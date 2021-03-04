package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import java.util.List;
import java.lang.Class;
import java.lang.Long;
import java.io.*;

public class RemoveLessCommand implements Command {
	@Override
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 1) {
			String message = "Command \"remove_less\" expects one argument - location.z, but ";
			if (args.length == 0) {
				message += "none are given.";
			} else {
				for (int i = 0; i < args.length; i++, message += i < args.length ? ", " : " are given.")
					message += "\"" + args[i] + "\"";
			}
			
			throw new CommandException(message);
		}
		
		final int z;
		try {
			z = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			context.getIO().writeWarning("Unable to parse the location.z.\n");
            return;
		}
		
		database.removeIf(x -> x.location.z < z);
	}
	
	@Override
	public String keyString() { return "remove_less"; }

	@Override
	public String description() { return "Remove values that have a location.z less than given."; }
	
}
