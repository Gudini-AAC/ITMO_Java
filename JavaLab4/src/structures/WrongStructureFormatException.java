package structures;
import java.lang.Exception;

public class WrongStructureFormatException extends Exception {
	public String toString() { return "Illigal value format"; }
	
	private String message;
}