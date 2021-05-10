package protocol;

import protocol.MessageType;
import protocol.Request;

public class RequestAccess extends Request {
	public MessageType getMessageType() { return MessageType.REQUEST_ACCESS; }
}
