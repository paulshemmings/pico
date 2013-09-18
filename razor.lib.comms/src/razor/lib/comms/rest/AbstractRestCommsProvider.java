package razor.lib.comms.rest;

import razor.lib.comms.ICommsRestProvider;
import razor.lib.comms.replyhandlers.ICommReplyHandler;
import razor.lib.comms.rest.handlers.CommsRequestQueue;
import razor.lib.comms.rest.handlers.ICommRestHandler;
import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.lib.models.messages.ICommReply;
import razor.lib.models.messages.ICommRequest;
import razor.lib.models.messages.rest.requests.ICommRestRequest;

public abstract class AbstractRestCommsProvider implements ICommsRestProvider {

	/**
	 * Build the REST specific request from the generic communication request
	 * @param request		: ICommRequest
	 * @param messageType	: int
	 * @return				: ICommRestRequest
	 */
	public abstract ICommRestRequest buildRestRequest(ICommRequest request, int messageType);
	
	/**
	 * Build the message specific REST response object to be returned
	 * @param messageType 	: int
	 * @return				: ICommRestResponse
	 */
	
	public abstract ICommRestResponse buildRestResponse(int messageType);
	
	/**
	 * Build the generic ICommReply object from the specific ICommRESTResponse
	 * @param response		: ICommRestResponse
	 * @return				: ICommReply
	 */
	public abstract ICommReply buildReply(ICommRestResponse response);
	
	/**
	 * Build the message specific handler that will populate the response from the request
	 * @param messageType	: int
	 * @return				: ICommRestHandler
	 */
	
	public abstract ICommRestHandler buildHandler(int messageType);

	/**
	 * Queue for all comms requests. If any duplicate requests come in before
	 * a response returns, then it is added to the distribution queue.
	 */
	
	CommsRequestQueue requestQueue = new CommsRequestQueue();
	
	/**
	 * REST implementation of the generic COMM request handle method
	 * @param messageType	: int
	 * @param replyHandler	: ICommReplyHandler
	 * @param request		: ICommRequest
	 */
	
	public void handleRequest(
			final int messageType,
			final ICommReplyHandler replyHandler, 
			final ICommRequest request
			) {		

		// build the request
		final ICommRestRequest restRequest = buildRestRequest(request, messageType);
		
		if( request!=null && restRequest != null && this.requestQueue.queueRequest(restRequest, replyHandler) == 1){
			// start a thread th handle the request
			Thread thread = new Thread(new Runnable() {
				public void run() {
					// build the response to populate
					ICommRestResponse restResponse = buildRestResponse(messageType);
					restResponse.setMessageType(messageType);
					// build the handler to populate the response
					ICommRestHandler handler = buildHandler(messageType);
					// populate the response
					handler.populateRestResponse(AbstractRestCommsProvider.this,restRequest,restResponse);									
					// if we have a handler for the reply, build the reply
					if(replyHandler!=null){
						// build the reply from the response
						ICommReply restReply = buildReply(restResponse);
						// notify handler of reply
						//replyHandler.onCommsReply(request, restReply);
						AbstractRestCommsProvider.this.requestQueue.distributeReply(request, restRequest, restReply);
					}
					
				}
			});
			thread.start();

		}
	}	
	
}
