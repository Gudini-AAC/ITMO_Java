package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import structures.WrongStructureFormatException;
import structures.Location;
import java.util.List;
import java.lang.Class;
import java.lang.Long;
import java.io.*;

public class FilterLocationCommand implements Command {
	@Override
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		try {
			Location location = Location.fromStream(reader, writer);
			List<Person> persons = database.retrieveIf(x -> x.location.equals(location));
			
			for (Person person : persons)
				writer.write(person.toString());
			writer.flush();		
		} catch (WrongStructureFormatException e) {
			writer.write(e.toString());
            writer.flush();
		}
	}
	
	@Override
	public String keyString() { return "filter_by_location"; }

	@Override
	public String description() { return "Print all of the values with a certan location."; }
	
}
