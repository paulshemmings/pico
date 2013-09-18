package razor.lib.comms.messages;

import razor.lib.models.messages.ICommReply;
import razor.lib.models.messages.ICommRequest;

public interface ICommFactory {
	ICommRequest CreateRequest(int message);
	ICommReply CreateReply(int message);
}
