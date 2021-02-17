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
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length != 0)
			CommandException.throwTooManyArgs(keyString(), args);
		
		try {
			for (int i = 0; i < CommandRegestry.regestry.length; i++) {
				Command command = (Command)(CommandRegestry.regestry[i].newInstance());
				writer.write(String.format("%d: %s: %s\n", i, command.keyString(), command.description()));
			}
		} catch (Exception e) {
			writer.write(String.format("Internal error: corrupted command regestry."));
		}
		
		writer.flush();
	}
	
	@Override
	public String keyString() { return "help"; }

	@Override
	public String description() { return "Print help for all commands."; }
	
}
