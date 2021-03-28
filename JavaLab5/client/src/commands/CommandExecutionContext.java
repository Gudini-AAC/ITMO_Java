package commands;

import structures.InteractionStreams;
import commands.CommandException;
import java.io.*;

/**
* @brief Context of a running command.
*        Holds user io handles and
*        depth of recursivly called
*        commands. 
*/
public class CommandExecutionContext {
	
	/**
	* @brief Constructs context with a given user io handle
	*        and call depth.
	*/
	private CommandExecutionContext(InteractionStreams userIO, long callDepth) {
		this.userIO    = userIO;
		this.callDepth = callDepth;
	}
	
	/**
	* @brief Constructs context with a given user io handle.
	* @param userIO User io handle to set.
	*/
	public CommandExecutionContext(InteractionStreams userIO) {
		this(userIO, 0);
	}
	
	/**
	* @brief Retrieve the user io handle.
	* @return User io handle.
	*/
	public InteractionStreams getIO() { return userIO; }

	/**
	* @brief  Get an execution context for the next call.
	* @throws CommandException if call depths exceeds the
	*         maximum stack depth. 
	* @return New CommandExecutionContext. 
	*/
	public CommandExecutionContext newCall() throws CommandException { 
		return newCall(userIO);
	}
	
	/**
	* @brief  Get an execution context for the next call
	*         with a given user io handle.
	* @param  userIO User io handle to set.
	* @throws CommandException if call depths exceeds the
	*         maximum stack depth. 
	* @return New CommandExecutionContext. 
	*/
	public CommandExecutionContext newCall(InteractionStreams userIO) throws CommandException {
		if (callDepth > MAX_DEPTH) {
			String message = String.format("Command exceeded the maximum call stack depth of %d calls. Probably it has a circular dependency.", MAX_DEPTH);
			throw new CommandException(message);
		}
		
		CommandExecutionContext ret = new CommandExecutionContext(userIO, callDepth + 1);
		return ret;
	}
	
	private InteractionStreams userIO;
	private long callDepth;
	private static final long MAX_DEPTH = 128;
}