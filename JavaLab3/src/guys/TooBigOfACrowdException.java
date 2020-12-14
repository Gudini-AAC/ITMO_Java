package guys;
import java.lang.Exception;

public class TooBigOfACrowdException extends Exception {
	public String getMessage() {
		return "Looks like this crowd isn't coronavirus-safe";
	}
}