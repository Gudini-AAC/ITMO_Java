package protocol;

import protocol.MessageType;
import java.io.Serializable;

public interface Request extends Serializable {
	MessageType getMessageType();
}

