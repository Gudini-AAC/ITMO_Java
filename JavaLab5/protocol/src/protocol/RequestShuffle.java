package protocol;

import protocol.MessageType;
import protocol.Request;

public class RequestShuffle extends Request {
	public MessageType getMessageType() { return MessageType.REQUEST_SHUFFLE; }
}
