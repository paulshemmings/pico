package razor.android.pico.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import razor.android.pico.activities.TrackerApplication;
import razor.android.pico.providers.PicoProvider;
import razor.android.pico.viewmodels.EventViewModel;
import razor.lib.comms.pico.R;
import razor.lib.comms.replyhandlers.ICommReplyHandler;
import razor.android.lib.core.CoreApplication;
import razor.android.lib.core.handlers.AbstractHandler;
import razor.android.lib.core.interfaces.ICoreViewModel;
import razor.lib.models.messages.ICommReply;
import razor.lib.models.messages.ICommRequest;
import razor.lib.models.messages.ModelReply;
import razor.lib.models.messages.pico.EventListRequest;
import razor.lib.models.messages.pico.EventLoadRequest;
import razor.lib.models.pico.EventCollectionDTO;
import razor.lib.models.pico.EventDTO;
import android.content.Context;
import android.location.Location;

import com.google.android.gms.maps.model.LatLngBounds;

public class EventHandler extends AbstractHandler implements ICommReplyHandler   {

	/**
	 * Published interfaces
	 * @author phemmings
	 */
	
	public interface onEventsListedListener{
		public void onEventItemsLoaded(List<ICoreViewModel> eventItems);
		public void onEventItemsLoadFailed(String errorMessage);
	}
	
	public interface onEventLoadedListener{
		public void onEventLoaded();
		public void onEventLoadFailed(String errorMessages);
	}
	
	/*
	 * Properties
	 */
	
	protected onEventLoadedListener EventLoadListener;
	protected onEventsListedListener EventsListedlistener;
	
	/**
	 * Constructor
	 * @param parentContext
	 */
	
	public EventHandler(Context parentContext) {
		super(parentContext);
	}
	
	/**
	 * Upload a new event
	 * @param listener
	 * @param imagePath
	 * @param tags
	 * @param title
	 */
	
	public void uploadEvent(onEventLoadedListener listener, String imagePath, String tags, String title){
		this.EventLoadListener = listener;  
		
    	EventDTO eventDto = new EventDTO();
    	eventDto.setTags(tags);
    	eventDto.setTitle(title);
    	eventDto.setCreatorId(TrackerApplication.instance().getUniqueIdentifier().toString());
					
		EventLoadRequest loadRequest = new EventLoadRequest();
		loadRequest.setEventDetails(eventDto);
		loadRequest.setEventUrl(imagePath);
							
		TrackerApplication trackerApp = (TrackerApplication) CoreApplication.getCoreApplication();
		Location bestLocation = trackerApp.getCurrentBestLocation();
		if(bestLocation!=null){
			loadRequest.getEventDetails().setLatitude(bestLocation.getLatitude());
			loadRequest.getEventDetails().setLongitude(bestLocation.getLongitude());
		}
		
		PicoProvider.instance().handleRequest(R.string.storeEventRequest, this, loadRequest);
	}
	
	/**
	 * List Events
	 * @param listener
	 * @param request
	 */
	
	public void listEvents(onEventsListedListener listener, boolean includeImages, LatLngBounds bounds){
		this.EventsListedlistener = listener;
		
		Date date = new Date();
		long earliestEventTime = date.getTime() - (86400000*2);
        
        EventListRequest request = new EventListRequest();
        request.setEarliestEventTime(earliestEventTime);
        request.setIncludeImageData(includeImages);

        if(bounds == null){
			TrackerApplication trackerApp = (TrackerApplication) CoreApplication.getCoreApplication();
			Location bestLocation = trackerApp.getCurrentBestLocation();
			if(bestLocation!=null){
				request.setBoundsFromPoint(bestLocation.getLatitude(), bestLocation.getLongitude(), 5);
			}
        }else{
        	request.setSouthWest(bounds.southwest.latitude, bounds.southwest.longitude);
        	request.setNorthEast(bounds.northeast.latitude, bounds.northeast.longitude);
        }
        
		PicoProvider.instance().handleRequest(R.string.listEventsRequest, this, request);
	}
	
	/**
	 * Handle reply
	 */
	
	public void onCommsReply(ICommRequest request, ICommReply reply) {
		
		if(reply.getMessageType() == R.string.listEventsRequest){	
			if(reply.getResponseCode()==0){
				ModelReply<EventCollectionDTO> modelReply = (ModelReply<EventCollectionDTO>) reply;
				EventCollectionDTO eventCollection = modelReply.getModel();
				
				List<ICoreViewModel> list = new ArrayList<ICoreViewModel>();			
				if( eventCollection!=null && eventCollection.size()>0 ){								
					for(EventDTO item: eventCollection){
						EventViewModel model = new EventViewModel(); 
						model.setModel(item);
						list.add(model);
					} 
				}
				this.EventsListedlistener.onEventItemsLoaded(list);	
			}else{
				this.EventsListedlistener.onEventItemsLoadFailed(reply.getErrorMessage());
			}
		} else if (reply.getMessageType() == R.string.storeEventRequest){
			if(reply.getResponseCode()==0){
				this.EventLoadListener.onEventLoaded();
			}else{
				this.EventLoadListener.onEventLoadFailed(reply.getErrorMessage());
				
			}
		}
	}

}
