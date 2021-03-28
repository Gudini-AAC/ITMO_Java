package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseShuffle extends Response {
	public ResponseShuffle(boolean success) { super.success = success; }
	public MessageType getMessageType() { return MessageType.REQUEST_SHUFFLE; }
}
