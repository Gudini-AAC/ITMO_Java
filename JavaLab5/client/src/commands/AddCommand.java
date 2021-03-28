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
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
			
		try {
			Person person = new Person();
			person.fromStream(context.getIO());
			database.add(person);
		} catch (WrongStructureFormatException e) {
			context.getIO().writeWarning(e.toString());
		}
		
	}
	
	@Override
	public String keyString() { return "add"; }

	@Override
	public String description() { return "Add value to the database."; }
	
}
