package razor.lib.comms.rest.handlers;

import razor.lib.comms.ICommsRestProvider;
import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.lib.models.messages.rest.requests.ICommRestRequest;

public interface ICommRestHandler {
	void populateRestResponse(ICommsRestProvider restProvider,ICommRestRequest restRequest, ICommRestResponse restResponse);
}
