package razor.lib.models.pico;

import java.io.Serializable;

import razor.lib.models.interfaces.IModel;

import com.google.gson.Gson;

public class EventDTO implements IModel, Serializable {

	// input & output
	
	private Long eventId;
    private String creatorId;	
	private String title;
	private Double latitude;
	private Double longitude;
	private String tags;	
	private byte[] imageData;
	
	// just output
	
	private Long createDateTime;
	private String friendlyCreateDateTime;
	
		
	public String toJson(){ 
		String json = new Gson().toJson(this);
		return json;
	}	
	
	public static EventDTO fromJson(String json){
		return new Gson().fromJson(json, EventDTO.class);
	}
	
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}
	public String getTags() {
		return tags;
	}
	public void setCreateDateTime(Long createDateTime) {
		this.createDateTime = createDateTime;
	}

	public Long getCreateDateTime() {
		return createDateTime;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public void setFriendlyCreateDateTime(String friendlyCreateDateTime) {
		this.friendlyCreateDateTime = friendlyCreateDateTime;
	}

	public String getFriendlyCreateDateTime() {
		return friendlyCreateDateTime;
	}
	
}
