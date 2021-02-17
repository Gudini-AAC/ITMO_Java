package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import java.util.List;
import java.lang.Class;
import java.lang.Long;
import java.io.*;

public class AscendingHeightCommand implements Command {
	@Override
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		List<Person> persons = database.sortedBy(
			(x, y) -> (x.height > y.height ? 1 : x.height < y.height ? -1 : 0)
		);
		
		for (Person person : persons)
			writer.write(person.toString());
		writer.flush();		
	}
	
	@Override
	public String keyString() { return "print_field_ascending_height"; }

	@Override
	public String description() { return "Print values in the ascending height order."; }
	
}
