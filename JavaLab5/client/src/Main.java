import java.io.*;
import database.Database;
import database.Server;
import java.lang.Exception;
import commands.CommandRunner;
import commands.CommandExecutionContext;
import structures.InteractionStreams;

public class Main {
	
	public static void main(String[] args) {
		
		Server server = null;
		try {
			server = new Server("localhost", 5454);
		} catch (IOException e) {
			System.out.println("Network access is denied. Exiting.");
            System.exit(65);
		}
		
		Database database = new Database(server);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		InteractionStreams userIO = new InteractionStreams(reader, writer, writer);
		CommandExecutionContext context = new CommandExecutionContext(userIO);
		
		for (;;) {
			try {
				String str = reader.readLine();
				String[] substrs = str.split(" ");
				if (substrs.length == 1 && substrs[0].equals("exit")) break;
				
				CommandRunner.runCommand(database, substrs, context);
			} catch (Exception e) {
				System.out.println(e.toString());
			} 
		}
		
		
		try {
			server.stop();
		} catch (IOException e) {
			System.out.println("Network access is denied. Exiting.");
            System.exit(65);
		}
	}
	
}