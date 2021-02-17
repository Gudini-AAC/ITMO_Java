package commands;

import commands.Command;
import commands.CommandException;
import commands.CommandRegestry;
import database.Database;
import structures.Person;
import java.io.*;
import java.lang.Class;

class NullOutputStream extends OutputStream {
	@Override
	public void write(int a) throws IOException {}
}

public class RunScriptCommand implements Command {
	@Override
	public void execute(Database database, BufferedReader reader, BufferedWriter writer, String[] args) throws CommandException, IOException {
		if (args.length == 0)
			throw new CommandException("Command \"execute_script\" expects one argument - script_file_path, but none are given.");
		
		String filepath = "";
		for (String arg : args)
			filepath += arg;
		
		File file = new File(filepath);
		FileInputStream fileIStream;

		try {
			fileIStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			writer.write("File not found\n");
			writer.flush();
			return;
		}

		BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileIStream));
		BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new NullOutputStream()));
		
		for (;;) {
			try {
				String str = fileReader.readLine();
				if (str == null) break;
				String[] substrs = str.split(" ");
				if (substrs.length == 1 && substrs[0].equals("exit")) break;
				
				CommandRunner.runCommand(substrs, database, fileReader, fileWriter);
			} catch (IOException e) {
				writer.write(e.toString());
				break;
			} 
		}
		
		writer.flush();
		fileIStream.close();
	}
	
	@Override
	public String keyString() { return "execute_script"; }

	@Override
	public String description() { return "Execute script from a file."; }
	
}
