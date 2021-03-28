package protocol;

import protocol.MessageType;
import protocol.Request;
import structures.Person;

public class RequestFind implements Request {
	public RequestFind(long id) { this.id = id; }
	public MessageType getMessageType() { return MessageType.REQUEST_FIND; }
	public long getId() { return id; }
	private long id;
}
