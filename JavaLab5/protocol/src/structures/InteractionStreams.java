package structures;

import java.io.*;

/**
* @brief Basic io stuff for an interactive communication with the user.
*/
public class InteractionStreams {
    
    /**
    * @brief Construct an interactive stream with a given io handles.
    * @param reader   User input handle.
    * @param requests User output handle for outputting requests.
    * @param warnings User output handle for outputting warnings.
    */
    public InteractionStreams(BufferedReader reader, BufferedWriter requests, BufferedWriter warnings) {
        this.reader   = reader;
        this.requests = requests;
        this.warnings = warnings;
    }
    
    /**
    * @brief  Read one line from the input stream.
    * @throws IOException If the input stream fails.
    * @return String line from the input stream.
    */
    public String readLine() throws IOException { return reader.readLine(); }
    
    /**
    * @brief  Write a request to the user to give some information.
    * @param  message A message to the user.
    * @throws IOException if the requests stream fails.
    */
    public void writeRequest(String message) throws IOException { requests.write(message); requests.flush(); }
    
    /**
    * @brief  Write a warning to the user.
    * @param  message A message to the user.
    * @throws IOException If the warnings stream fails.
    */
    public void writeWarning(String message) throws IOException { warnings.write(message); warnings.flush(); }
    
    public BufferedReader getReader()   { return reader;   }
    public BufferedWriter getRequests() { return requests; }
    public BufferedWriter getWarnings() { return warnings; }
    
    private BufferedReader reader;
    private BufferedWriter requests;
    private BufferedWriter warnings;
}
