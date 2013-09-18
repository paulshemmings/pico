package razor.lib.models.messages.pico;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import razor.lib.models.helpers.EncryptionHelper;
import razor.lib.models.messages.rest.requests.ICommRestMultiPartRequest;
import razor.lib.models.messages.rest.requests.SimpleRestRequest;
import razor.lib.models.pico.EventDTO;

import com.google.gson.annotations.SerializedName;

public class EventLoadRequest extends SimpleRestRequest implements ICommRestMultiPartRequest {

	@SerializedName("EventDto") 
	private EventDTO eventDetails;
	
	@SerializedName("EventUrl") 
	private String EventUrl;

	
	public EventLoadRequest() {
		super(null);
	}
	
	public void setEventDetails(EventDTO EventDetails) {
		this.eventDetails = EventDetails;
	}

	public EventDTO getEventDetails() {
		return eventDetails;
	}

	public void setEventUrl(String EventUrl) {
		this.EventUrl = EventUrl;
	}

	public String getEventUrl() {
		return EventUrl;
	}

	@Override
	public List<NameValuePair> getNameValuePairs() {
		
		String json = this.eventDetails.toJson();
		String encryptedJson = new EncryptionHelper().encryptString(EncryptionString.KEY,json);
		
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		
		nameValuePairs.add(new BasicNameValuePair("EventDto",encryptedJson));
		nameValuePairs.add(new BasicNameValuePair("FILE",this.getEventUrl()));
		return nameValuePairs;
	}
	
}
