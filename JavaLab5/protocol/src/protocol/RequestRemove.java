package protocol;

import java.util.function.Predicate;

import protocol.MessageType;
import protocol.Request;
import structures.Person;
import java.lang.Long;

public class RequestRemove extends Request {
	public enum Key {
		ID,
		LOCATION_Z_GREATER,
		LOCATION_Z_LESS
	}
	
	public RequestRemove(Key key, Long value) { this.key = key; this.value = value; }
	public MessageType getMessageType() { return MessageType.REQUEST_REMOVE; }
	public Key getKey() { return key; }
	public Long getValue() { return value; }
	private Key key;
	private Long value;
}
