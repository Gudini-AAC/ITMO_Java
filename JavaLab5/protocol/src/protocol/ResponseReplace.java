package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseReplace extends Response {
	public ResponseReplace(boolean success) { super.success = success; }
	public MessageType getMessageType() { return MessageType.REQUEST_REPLACE; }
}
