package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import java.io.*;
import java.lang.Class;

public class ShowCommand implements Command {
	@Override
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		context.getIO().writeWarning(database.toString());
	}
	
	@Override
	public String keyString() { return "show"; }

	@Override
	public String description() { return "Print all the values of the database."; }
	
}
