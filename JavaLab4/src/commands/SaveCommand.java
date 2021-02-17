package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import java.io.*;
import java.lang.Class;

public class SaveCommand implements Command {
	@Override
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		database.save(writer);
	}
	
	@Override
	public String keyString() { return "save"; }

	@Override
	public String description() { return "Save the database as a CSV file."; }
	
}
