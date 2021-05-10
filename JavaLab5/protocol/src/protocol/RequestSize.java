package protocol;

import protocol.MessageType;
import protocol.Request;

public class RequestSize extends Request {
	public MessageType getMessageType() { return MessageType.REQUEST_SIZE; }
}
