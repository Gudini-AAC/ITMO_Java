package commands;

import commands.*;
import database.Database;
import java.io.OutputStream;
import java.lang.Class;

/**
* @brief CommandRegestry is a class that stores all of the avalible command classes.
*        If you want to write a new command, you can just add it here and it will be
*        added to the interactive user interface.
*/
public class CommandRegestry {
	
	/**
	* @brief Actual array of commands
	*/
	public static final Class[] regestry = {
		HelpCommand.class,
		InfoCommand.class,
		ShowCommand.class,
		AddCommand.class,
		UpdateCommand.class,
		RemoveCommand.class,
		ClearCommand.class,
		RunScriptCommand.class,
		ExitCommand.class,
		ShuffleCommand.class,
		RemoveGreaterCommand.class,
		RemoveLessCommand.class,
		FilterLocationCommand.class,
		AscendingCommand.class,
		AscendingHeightCommand.class,
	};
}
