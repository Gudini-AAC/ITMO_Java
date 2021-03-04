package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import structures.WrongStructureFormatException;
import java.lang.Class;
import java.lang.Long;
import java.io.*;

public class UpdateCommand implements Command {
	@Override
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 1) {
			String message = "Command \"update\" expects one argument - id, but ";
			if (args.length == 0) {
				message += "none are given.";
			} else {
				for (int i = 0; i < args.length; i++, message += i < args.length ? ", " : " are given.")
					message += "\"" + args[i] + "\"";
			}
			
			throw new CommandException(message);
		}
		
		final long id;
		try {
			id = Long.parseLong(args[0]);
		} catch (NumberFormatException e) {
			throw new CommandException("Unable to parse the id.\n");
		}
		
		int index = database.findFirstOf(x -> x.id == id);
		if (index == -1)
			throw new CommandException("Element not found.\n");
		
		try {
			Person person = new Person();
			person.fromStream(context.getIO());
			person.id = id;
			database.replace(index, person);
		} catch (WrongStructureFormatException e) {
			throw new CommandException(e.toString());
		}
	}
	
	@Override
	public String keyString() { return "update"; }

	@Override
	public String description() { return "Update value in the database."; }
	
}
