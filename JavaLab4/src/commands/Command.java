package commands;

import commands.CommandExecutionContext;
import commands.CommandException;
import database.Database;
import java.io.*;

/**
* @brief Interface for all of the commands.
*        If you want to write one, add it to the CommandRegestry.
*/
public interface Command {
	
	/**
	* @brief Execute the command.
	*
	* @param database For command to change it.
	* @param args Trailing strings that are given with the command.
	* @param context Context of a running command call stack.
	* @throws CommandException If the command fails.
	* @throws IOException If the read or write streams fail.
	*/
	void execute(Database database, String[] args, CommandExecutionContext context) throws CommandException, IOException;
		
	/**
	* @return The string that is used to call the command.
	*/
	String keyString();
	
	/**
	* @return Description of the command.
	*/
	String description();
}
