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
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		writer.write(database.toString());
		writer.flush();
	}
	
	@Override
	public String keyString() { return "show"; }

	@Override
	public String description() { return "Print all the values of the database."; }
	
}
