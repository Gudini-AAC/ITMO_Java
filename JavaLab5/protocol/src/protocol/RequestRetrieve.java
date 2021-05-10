package protocol;

import java.util.function.Predicate;
import protocol.SerializablePredicate;
import protocol.MessageType;
import protocol.Request;
import structures.Person;
import structures.Location;

public class RequestRetrieve extends Request {
	public RequestRetrieve(Location location) { this.location = location; }
	public MessageType getMessageType() { return MessageType.REQUEST_RETRIEVE; }
	public Location getLocation() { return location; }
	private Location location;
}
