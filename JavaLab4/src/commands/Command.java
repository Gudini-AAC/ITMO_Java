package commands;

import commands.CommandException;
import database.Database;
import java.io.*;

public interface Command {
	void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException;
	String keyString();
	String description();
}
