package razor.lib.models.messages;

public interface ICommReply {
	 String getErrorMessage();
	 int getMessageType();
	 long getResponseCode();
}
