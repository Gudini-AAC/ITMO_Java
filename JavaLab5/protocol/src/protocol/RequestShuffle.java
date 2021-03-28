package protocol;

import protocol.MessageType;
import protocol.Request;

public class RequestShuffle implements Request {
	public MessageType getMessageType() { return MessageType.REQUEST_SHUFFLE; }
}
