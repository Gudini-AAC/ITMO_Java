package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import java.io.*;
import java.lang.Class;

public class InfoCommand implements Command {
	@Override
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		context.getIO().writeWarning(String.format("Type: Stack<Person>, Size: %d, Date: ", database.size()) + database.constructionDate().toString() + "\n");
	}
	
	@Override
	public String keyString() { return "info"; }

	@Override
	public String description() { return "Information about the database."; }
	
}
