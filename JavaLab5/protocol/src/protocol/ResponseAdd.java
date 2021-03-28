package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseAdd extends Response {
	public ResponseAdd(boolean success) { super.success = success; }
	public MessageType getMessageType() { return MessageType.REQUEST_ADD; }
}
