package protocol;

import java.util.Comparator;
import protocol.MessageType;
import protocol.Request;
import protocol.SerializableComparator;
import structures.Person;

public class RequestSorted extends Request {
	public enum Key {
		LOCATION_Z,
		HEIGHT
	}
	
	public RequestSorted(Key key) { this.key = key; }
	public MessageType getMessageType() { return MessageType.REQUEST_SORTED; }
	public Key getKey() { return key; };
	private Key key;
}
