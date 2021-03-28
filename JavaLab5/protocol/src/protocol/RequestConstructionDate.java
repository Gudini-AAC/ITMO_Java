package protocol;

import protocol.MessageType;
import protocol.Request;

public class RequestConstructionDate implements Request {
	public MessageType getMessageType() { return MessageType.REQUEST_CONSTRUCTUION_DATE; }
}
