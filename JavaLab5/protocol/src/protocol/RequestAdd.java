package protocol;

import protocol.MessageType;
import protocol.Request;
import structures.Person;

public class RequestAdd extends Request {
	public RequestAdd(Person person) { this.person = person; }
	public MessageType getMessageType() { return MessageType.REQUEST_ADD; }
	public Person getPerson() { return person; }
	private Person person;
}
