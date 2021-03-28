package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseFind extends Response {
	public ResponseFind() { super.success = false; }
	public ResponseFind(int index) { super.success = true; this.index = index; }
	public MessageType getMessageType() { return MessageType.REQUEST_FIND; }
	public int getIndex() { return index; }
	private int index;
}
