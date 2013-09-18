package razor.lib.comms.pico.rest;

import razor.lib.comms.ICommsProvider;
import razor.lib.comms.ICommsRestProvider;
import razor.lib.comms.pico.R;
import razor.lib.comms.rest.handlers.BitmapJsonRestHandler;
import razor.lib.comms.rest.handlers.ICommRestHandler;
import razor.lib.comms.rest.handlers.MultiElementJsonRestHandler;
import razor.lib.comms.rest.handlers.SimpleJsonRestHandler;
import razor.lib.comms.rest.responses.GenericRestResponse;
import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.lib.comms.rest.responses.SimpleRestResponse;
import razor.android.lib.core.CoreApplication;
import razor.lib.models.helpers.JsonHelper;
import razor.lib.models.messages.ICommReply;
import razor.lib.models.messages.ICommRequest;
import razor.lib.models.messages.ModelReply;
import razor.lib.models.messages.StatusReply;
import razor.lib.models.messages.pico.EventCollectionResponse;
import razor.lib.models.messages.pico.StoreEventResponse;
import razor.lib.models.messages.rest.requests.ICommRestRequest;
import razor.lib.models.messages.rest.requests.SimpleJsonRestRequest;
import razor.lib.models.pico.EventCollectionDTO;
import android.graphics.Bitmap;


public class CommsProvider extends razor.lib.comms.rest.AbstractRestCommsProvider implements ICommsRestProvider {
	
	private String sessionId;	
	private static ICommsProvider instance; 
	
	/**
	 * Return the single instance of this Comms provider
	 */
	
	public static ICommsProvider getInstance() {
		if (instance == null) {
			instance = new CommsProvider();
		}
		return instance;
	}

	public void setSessionId(String sessionId) {this.sessionId = sessionId;}
	public String getSessionId() {return sessionId;}

	/**
	 * Build the rest request based on the ICommRequest object
	 * @see com.bbconnect.comms.rest.AbstractRestCommsProvider#buildRestRequest(com.bbconnect.comms.messages.ICommRequest, int)
	 */
	
	public ICommRestRequest buildRestRequest(ICommRequest request, int messageType){

		String messageUrl = CoreApplication.getCoreApplicationContext().getResources().getString(messageType);
		ICommRestRequest restRequest = null;
		if (messageType == R.string.storeEventRequest) {
			restRequest = (ICommRestRequest) request;
			restRequest.setCommand(messageUrl);
		} else if( messageType == R.string.loadPhotoRequest){
			restRequest = (ICommRestRequest) request;
			restRequest.setCommand(messageUrl);
		} else {
			restRequest = new SimpleJsonRestRequest(request,messageUrl);
		}
		return restRequest;
	}

	/**
	 * Return an instance of the ICommRestResponse that is to be filled in
	 * @see com.bbconnect.comms.rest.AbstractRestCommsProvider#buildRestResponse()
	 */
	
	@Override
	public ICommRestResponse buildRestResponse(int messageType) {
		ICommRestResponse restResponse = null;
		
		if(messageType == R.string.loadPhotoRequest){
			restResponse = new GenericRestResponse<Bitmap>();
		}else{
			restResponse = new SimpleRestResponse();
		}
		return restResponse;
	}	
	
	/**
	 * Return instance of REST handler to use to populate ICommRestResponse
	 * @see com.bbconnect.comms.rest.AbstractRestCommsProvider#buildHandler()
	 */
	
	@Override
	public ICommRestHandler buildHandler(int messageType) {
		ICommRestHandler restHandler = null;
		if (messageType == R.string.storeEventRequest) {
			restHandler = new MultiElementJsonRestHandler();
		} else if (messageType == R.string.loadPhotoRequest){
			restHandler = new BitmapJsonRestHandler();
		} else{
			restHandler = new SimpleJsonRestHandler();
		}
		return restHandler;
	}
	
	/** 
	 * Build the REST reply from the ICommRestResponse object
	 * @see com.bbconnect.comms.rest.AbstractRestCommsProvider#buildReply(com.bbconnect.comms.rest.responses.ICommRestResponse)
	 */
	
	public ICommReply buildReply(ICommRestResponse restResponse){
		
		ICommReply builtReply = null;
		
		if(restResponse.getResponseCode()>0){
			StatusReply statusReply = new StatusReply(restResponse.getMessageType());
			statusReply.setErrorMessage(restResponse.getErrorMessage());
			statusReply.setResponseCode(restResponse.getResponseCode());			
			return statusReply;
		}
		
		if( restResponse.getMessageType() == R.string.listEventsRequest){
			
			JsonHelper<EventCollectionResponse> helper = new JsonHelper<EventCollectionResponse>(EventCollectionResponse.class);
			EventCollectionResponse eventCollectionResponse = helper.fromJson((String)restResponse.getResponseContent());
			
			ModelReply<EventCollectionDTO> modelReply = new ModelReply<EventCollectionDTO>(restResponse.getMessageType());
			modelReply.addItem(eventCollectionResponse.getResponseData());		
			modelReply.setResponseCode(eventCollectionResponse.getResponseCode());
			modelReply.setErrorMessage(eventCollectionResponse.getErrorMessage());
			builtReply = modelReply;
			
		}else if ( restResponse.getMessageType() == R.string.loadPhotoRequest){
			
			Bitmap bmImg = (Bitmap) restResponse.getResponseContent();			
			ModelReply<Bitmap> modelReply = new ModelReply<Bitmap>(restResponse.getMessageType());
			
			modelReply.addItem(bmImg);
			modelReply.setResponseCode(restResponse.getResponseCode());
			modelReply.setErrorMessage(restResponse.getErrorMessage());			
			builtReply = modelReply;
				
		}else if( restResponse.getMessageType() == R.string.storeEventRequest){
			
			JsonHelper<StoreEventResponse> helper = new JsonHelper<StoreEventResponse>(StoreEventResponse.class);
			StoreEventResponse storeEventResponse = helper.fromJson((String)restResponse.getResponseContent());
			
			ModelReply<Long> modelReply = new ModelReply<Long>(restResponse.getMessageType());
			modelReply.addItem(storeEventResponse.getResponseData());
			modelReply.setResponseCode(storeEventResponse.getResponseCode());
			modelReply.setErrorMessage(storeEventResponse.getErrorMessage());			
			builtReply = modelReply;
		}
		
		return builtReply;
	}

	@Override
	public boolean isFacingProduction() {
		return false;
	}

	@Override
	public String getUrl() {
		return CoreApplication.getCoreApplicationContext().getResources().getString(R.string.coreRestUrl);
	}
	

	
	
}
