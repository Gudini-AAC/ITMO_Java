package protocol;

import protocol.MessageType;
import protocol.Request;

public class RequestClear extends Request {
	public MessageType getMessageType() { return MessageType.REQUEST_CLEAR; }
}
