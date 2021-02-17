package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import java.io.*;
import java.lang.Class;

public class InfoCommand implements Command {
	@Override
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		writer.write(String.format("Type: Stack<Person>, Size: %d, Date: ", database.size()) + database.constructionDate().toString() + "\n");
		writer.flush();
	}
	
	@Override
	public String keyString() { return "info"; }

	@Override
	public String description() { return "Information about the database."; }
	
}
