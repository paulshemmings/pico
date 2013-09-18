package razor.lib.comms.rest.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import razor.lib.comms.replyhandlers.ICommReplyHandler;
import razor.android.lib.core.helpers.LogHelper;
import razor.lib.models.messages.ICommReply;
import razor.lib.models.messages.ICommRequest;

/**
 * Queue for all comms requests. If any duplicate requests come in before
 * a response returns, then it is added to the distribution queue.
 * @author phemmings
 */

public class CommsRequestQueue {

	private static final String TAG = "CommsRequestQueue";
	private Map<ICommRequest, List<ICommReplyHandler>> requestQueue = null;
	
	/**
	 * Queue the handlers that are waiting for a response for a particular request
	 * @param request
	 * @param handler
	 * @return
	 */
	
	public synchronized int queueRequest(ICommRequest request, ICommReplyHandler handler){
		int requestQueueSize = 0;
		try{		
			if(requestQueue==null){
				LogHelper.i(TAG,"build request queue");
				requestQueue = new HashMap<ICommRequest, List<ICommReplyHandler>>();
			}
			if(!requestQueue.containsKey(request)){
				LogHelper.i(TAG,"build list for request %s",request.toString());
				requestQueue.put(request, new ArrayList<ICommReplyHandler>());
			}
			requestQueue.get(request).add(handler);
			requestQueueSize = requestQueue.get(request).size();
		}
		catch(Exception ex){
			LogHelper.e(TAG, "queueRequest failed with %s",ex.getMessage());
		}
		return requestQueueSize;
	}	
	
	/**
	 * Distributes the response to all the reply handlers waiting for that exact request
	 * @param request
	 * @param restRequest
	 * @param reply
	 */
	
	public void distributeReply(ICommRequest request, ICommRequest restRequest, ICommReply reply ){
		try{
			if(requestQueue!=null){
				if(requestQueue.containsKey(restRequest)){
					for(ICommReplyHandler handler:requestQueue.get(restRequest)){
						if(handler!=null){
							LogHelper.i(TAG,"distributing a reply for %s",request.toString());
							handler.onCommsReply(request, reply);
						}
					}
					requestQueue.remove(restRequest);
				}
			}
		}
		catch(Exception ex){
			LogHelper.e(TAG, "queueRequest failed with %s",ex.getMessage());
		}
	}
}
