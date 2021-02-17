package commands;
import java.lang.Exception;

public class CommandException extends Exception {
	public CommandException(String message) { this.message = message; }
	public String toString() { return message; }
	
	public static void throwTooManyArgs(String cmd, String[] args) throws CommandException {
		String message = "Command \"" + cmd + "\" does not expect any arguments, but ";
		for (int i = 0; i < args.length; i++, message += i < args.length ? ", " : " are given.")
			message += "\"" + args[i] + "\"";
		throw new CommandException(message);
	}
	
	private String message;
}