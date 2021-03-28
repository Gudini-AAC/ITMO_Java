package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseRemove extends Response {
	public ResponseRemove(boolean success) { super.success = success; }
	public MessageType getMessageType() { return MessageType.REQUEST_REMOVE; }
}
