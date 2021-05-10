package protocol;

import protocol.MessageType;
import protocol.Request;

public class RequestRegister extends Request {
	public MessageType getMessageType() { return MessageType.REQUEST_REGISTER; }
}
