package commands;

import commands.*;
import database.Database;
import java.io.OutputStream;
import java.lang.Class;

public class CommandRegestry {
	public static Class[] regestry = {
		HelpCommand.class,
		InfoCommand.class,
		ShowCommand.class,
		AddCommand.class,
		UpdateCommand.class,
		RemoveCommand.class,
		ClearCommand.class,
		SaveCommand.class,
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
