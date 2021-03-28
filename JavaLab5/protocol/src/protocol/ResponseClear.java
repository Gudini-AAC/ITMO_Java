package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseClear extends Response {
	public ResponseClear(boolean success) { super.success = success; }
	public MessageType getMessageType() { return MessageType.REQUEST_CLEAR; }
}
