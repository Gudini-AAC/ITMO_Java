package protocol;

import protocol.MessageType;
import protocol.SessionData;
import java.io.Serializable;

public class Request implements Serializable {
	public MessageType getMessageType() { return MessageType.NONE; }
	
	public Request() { login = SessionData.login; password = SessionData.password; }
	
	public String login;
	public String password;
}

