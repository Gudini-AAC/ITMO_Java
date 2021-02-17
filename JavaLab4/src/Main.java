import java.io.*;
import database.Database;
import java.lang.Exception;
import commands.CommandRunner;

public class Main {
	public static void main(String[] args) {
		if (args.length == 0) { 
			System.out.println("Database path is not present. Provide one in the first argument.");
		}
		Database database = new Database(args[0]);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
		
		try {
			database.load(writer);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println(e.toString());
		}
		
		for (;;) {
			try {
				String str = reader.readLine();
				String[] substrs = str.split(" ");
				if (substrs.length == 1 && substrs[0].equals("exit")) break;
				
				CommandRunner.runCommand(substrs, database, reader, writer);
			} catch (Exception e) {
				System.out.println(e.toString());
			} 
		}
		
		
	}
}