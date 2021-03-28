package protocol;

import java.util.function.Predicate;
import protocol.SerializablePredicate;
import protocol.MessageType;
import protocol.Request;
import structures.Person;

public class RequestReplace implements Request {
	public RequestReplace(int index, Person person) { this.index = index; this.person = person; }
	public MessageType getMessageType() { return MessageType.REQUEST_REPLACE; }
	public Person getPerson() { return person; }
	public int getIndex() { return index; }
	private Person person;
	private int index;
}
