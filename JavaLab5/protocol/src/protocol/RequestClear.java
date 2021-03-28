package protocol;

import protocol.MessageType;
import protocol.Request;

public class RequestClear implements Request {
	public MessageType getMessageType() { return MessageType.REQUEST_CLEAR; }
}
