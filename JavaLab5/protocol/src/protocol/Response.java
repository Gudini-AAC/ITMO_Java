package protocol;

import protocol.MessageType;
import java.io.Serializable;

public abstract class Response implements Serializable {
	public abstract MessageType getMessageType();
	public boolean isSuccessful() { return success; }
	boolean success;
}
