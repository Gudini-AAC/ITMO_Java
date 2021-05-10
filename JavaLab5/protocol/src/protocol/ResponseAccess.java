package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseAccess extends Response {
	public ResponseAccess(boolean success) { super.success = success; }
	public MessageType getMessageType() { return MessageType.REQUEST_ACCESS; }
}
