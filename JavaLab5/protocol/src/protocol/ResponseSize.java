package protocol;

import protocol.MessageType;
import protocol.Response;

public class ResponseSize extends Response {
	public ResponseSize() { super.success = false; }
	public ResponseSize(int size) { super.success = true; this.size = size; }
	public MessageType getMessageType() { return MessageType.REQUEST_SIZE; }
	public int getSize() { return size; }
	private int size;
}
