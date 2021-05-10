package protocol;

import java.util.function.Predicate;
import protocol.SerializablePredicate;
import protocol.MessageType;
import protocol.Request;
import structures.Person;

public class RequestReplace extends Request {
	public RequestReplace(Person person) { this.person = person; }
	public MessageType getMessageType() { return MessageType.REQUEST_REPLACE; }
	public Person getPerson() { return person; }
	private Person person;
}
