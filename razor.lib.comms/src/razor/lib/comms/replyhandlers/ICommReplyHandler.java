package razor.lib.comms.replyhandlers;

import razor.lib.models.messages.ICommReply;
import razor.lib.models.messages.ICommRequest;

public interface ICommReplyHandler {
	void onCommsReply(ICommRequest request, ICommReply reply);
}
