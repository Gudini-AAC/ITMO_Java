package protocol;

import java.time.LocalDate;
import protocol.MessageType;
import protocol.Response;

public class ResponseConstructionDate extends Response {
	public ResponseConstructionDate() { super.success = false; }
	public ResponseConstructionDate(LocalDate date) { super.success = true; this.date = date; }
	public MessageType getMessageType() { return MessageType.REQUEST_CONSTRUCTUION_DATE; }
	public LocalDate getConstructionDate() { return date; }
	private LocalDate date;
}
