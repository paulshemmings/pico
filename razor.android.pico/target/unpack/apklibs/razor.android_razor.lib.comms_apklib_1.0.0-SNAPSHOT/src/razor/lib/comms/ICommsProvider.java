package razor.lib.comms;

import razor.lib.comms.replyhandlers.ICommReplyHandler;
import razor.lib.models.messages.ICommRequest;


public interface ICommsProvider {
	
	void handleRequest(
			final int messageType,
			final ICommReplyHandler replyHandler, 
			final ICommRequest request
			);

	String getSessionId();
	void setSessionId(String sessionId);
	
	boolean isFacingProduction();
}