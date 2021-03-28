package structures;

import structures.WrongStructureFormatException;
import java.io.*;

public interface Interactive {
	
	/**
    * @brief Load this object in an interactive manner.
    * @param userIO User io handle.
    * @throws IOException If one of the user io streams fails.
    * @throws WrongStructureFormatException If user input is invalid.
    */
	void fromStream(InteractionStreams userIO)  throws IOException, WrongStructureFormatException;
	
}

