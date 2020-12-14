package guys;
import java.lang.RuntimeException;

public class NothingToEatException extends RuntimeException {
	public String getMessage() {
		return "There is nothing to eat";
	}
}