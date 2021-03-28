package protocol;

import protocol.MessageType;
import protocol.Response;
import structures.Person;
import java.util.List;

public class ResponseRetrieve extends Response {
	public ResponseRetrieve() { super.success = false; }
	public ResponseRetrieve(List<Person> values) { super.success = true; this.values = values; }
	public MessageType getMessageType() { return MessageType.REQUEST_RETRIEVE; }
	public List<Person> getValues() { return values; }
	private List<Person> values;
}
