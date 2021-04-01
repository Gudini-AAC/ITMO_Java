package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import java.lang.Class;
import java.lang.Long;
import java.io.*;
import protocol.RequestRemove;

public class RemoveCommand implements Command {
	@Override
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 1) {
			String message = "Command \"remove_by_id\" expects one argument - id, but ";
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
			context.getIO().writeWarning("Unable to parse the id.\n");
            return;
		}
		
		if (!database.removeIf(RequestRemove.Key.ID, new Long(id)))
			context.getIO().writeWarning("No elements removed.\n");
	}
	
	@Override
	public String keyString() { return "remove_by_id"; }

	@Override
	public String description() { return "Remove the value from the database."; }
	
}
