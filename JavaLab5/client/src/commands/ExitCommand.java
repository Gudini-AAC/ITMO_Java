package commands;

import commands.Command;
import commands.CommandException;
import database.Database;
import java.io.*;

public class ExitCommand implements Command {
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		throw new CommandException("Internal error: exit cannot be executed.");
	}
	
	public String keyString() { return "exit"; };
	public String description() { return "Exit without saving."; };
}