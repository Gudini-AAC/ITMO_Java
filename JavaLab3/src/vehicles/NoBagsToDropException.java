package vehicles;
import java.lang.RuntimeException;

public class NoBagsToDropException extends RuntimeException {
	public String getMessage() {
		return "All of the bags are out, you're probably gonna crash";
	}
}