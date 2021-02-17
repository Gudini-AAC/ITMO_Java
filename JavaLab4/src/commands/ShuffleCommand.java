package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import java.io.*;
import java.lang.Class;

public class ShuffleCommand implements Command {
	@Override
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		database.shuffle();
	}
	
	@Override
	public String keyString() { return "shuffle"; }

	@Override
	public String description() { return "Shuffle the values in the database."; }
	
}
