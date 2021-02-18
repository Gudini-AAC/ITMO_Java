package commands;

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
	* @param database for command to change it.
	* @param reader for command to interact with the user.
	* @param writer for command to interact with the user.
	* @param args trailing strings that are given with the command.
	* @throws CommandException if the command fails.
	* @throws IOException if the read or write streams fail.
	*/
	void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args)
		throws CommandException, IOException;
		
	/**
	* @return The string that is used to call the command.
	*/
	String keyString();
	
	/**
	* @return Description of the command.
	*/
	String description();
}
