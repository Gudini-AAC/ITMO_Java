package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import java.io.*;
import java.lang.Class;
import java.lang.Exception;

public class HelpCommand implements Command {
	@Override
	public void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		try {
			for (int i = 0; i < CommandRegestry.regestry.length; i++) {
				Command command = (Command)(CommandRegestry.regestry[i].newInstance());
				context.getIO().writeWarning(String.format("%d: %s: %s\n", i, command.keyString(), command.description()));
			}
		} catch (InstantiationException | IllegalAccessException e) {
			throw new CommandException(String.format("Internal error: corrupted command regestry."));
		}
	}
	
	@Override
	public String keyString() { return "help"; }

	@Override
	public String description() { return "Print help for all commands."; }
	
}
