package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import java.io.*;
import java.time.LocalDate;
import java.lang.Class;

public class InfoCommand implements Command {
	@Override
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		LocalDate date = database.constructionDate();
		if (date != null) {
			int size = database.size();
			
			String message = String.format("Type: Stack<Person>, Size: %d, Date: ", size);
			context.getIO().writeWarning(message + date.toString() + "\n");
		}
		
		
	}
	
	@Override
	public String keyString() { return "info"; }

	@Override
	public String description() { return "Information about the database."; }
	
}
