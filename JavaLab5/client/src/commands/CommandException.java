package commands;
import java.lang.Exception;


/**
* @brief Thrown when command cannot be executed.
*/
public class CommandException extends Exception {
	public CommandException(String message) { this.message = message; }
	public String toString() { return message; }
	
	/**
	* @brief Throws CommandException with a formated message.
	* @param cmd Command name.
	* @param args Extra arguments.
	* @throws CommandException with a formated message.
	*/
	public static void throwTooManyArgs(String cmd, String[] args) throws CommandException {
		String message = "Command \"" + cmd + "\" does not expect any arguments, but ";
		for (int i = 0; i < args.length; i++, message += i < args.length ? ", " : " are given.")
			message += "\"" + args[i] + "\"";
		throw new CommandException(message);
	}
	
	private String message;
}