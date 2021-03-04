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

public class AscendingCommand implements Command {
	@Override
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		List<Person> persons = database.sortedBy(
			(x, y) -> (x.location.z > y.location.z ? 1 : x.location.z < y.location.z ? -1 : 0)
		);
		
		
		for (Person person : persons)
			context.getIO().writeWarning(person.toString());
	}
	
	@Override
	public String keyString() { return "print_ascending"; }

	@Override
	public String description() { return "Print values in the ascending order."; }
	
}
