package protocol;

import protocol.MessageType;
import protocol.Response;
import structures.Person;
import java.util.List;

public class ResponseSorted extends Response {
	public ResponseSorted() { super.success = false; }
	public ResponseSorted(List<Person> values) { super.success = true; this.values = values; }
	public MessageType getMessageType() { return MessageType.REQUEST_SORTED; }
	public List<Person> getValues() { return values; }
	private List<Person> values;
}
