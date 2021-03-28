package structures;
import java.lang.Exception;

/**
* @brief Thrown when structure cannot be loaded.
*/
public class WrongStructureFormatException extends Exception {
	public String toString() { return "Illigal value format"; }
}