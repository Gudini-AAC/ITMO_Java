package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseRegister extends Response {
	public ResponseRegister(boolean success) { super.success = success; }
	public MessageType getMessageType() { return MessageType.REQUEST_REGISTER; }
}
