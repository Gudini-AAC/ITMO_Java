package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import structures.WrongStructureFormatException;
import java.io.*;
import java.lang.Class;

public class AddCommand implements Command {
	@Override
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
			
		try {
			Person person = Person.fromStream(reader, writer);
			database.add(person);
		} catch (IOException | WrongStructureFormatException e) {
			writer.write(e.toString());
			writer.flush();
		}
		
	}
	
	@Override
	public String keyString() { return "add"; }

	@Override
	public String description() { return "Add value to the database."; }
	
}
