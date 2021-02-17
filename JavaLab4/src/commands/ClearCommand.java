package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import java.lang.Class;
import java.lang.Long;
import java.io.*;

public class ClearCommand implements Command {
	@Override
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		database.clear();
	}
	
	@Override
	public String keyString() { return "clear"; }

	@Override
	public String description() { return "Clear the database."; }
	
}
