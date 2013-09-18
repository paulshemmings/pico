package razor.lib.models.messages.pico;

import razor.lib.models.messages.ICommRequest;

import com.google.gson.annotations.SerializedName;

public class EventListRequest implements ICommRequest {
	
	public static class LatLng{
		@SerializedName("Latitude") 
		private Double latitude;		
		@SerializedName("Longitude") 
		private Double longitude;
		
		public LatLng(){			
		}
		public LatLng(double latitude, double longitude){
			this.latitude = latitude;
			this.longitude = longitude;
		}
		
		public Double getLatitude() {
			return latitude;
		}
		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}
		public Double getLongitude() {
			return longitude;
		}
		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}		
	}
	
	@SerializedName("DeviceId") 
	private String callerId;

	@SerializedName("SouthWest")
	private LatLng southWest;

	@SerializedName("NorthEast")
	private LatLng northEast;
	
	@SerializedName("EarliestEvent") 
	private Long earliestEventTime;

	@SerializedName("IncludeImageData") 
	private boolean includeImageData = false;
	
	public EventListRequest(){
	}
	
	public String getCallerId() {
		return callerId;
	}
	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	public void setEarliestEventTime(Long earliestEventTime) {
		this.earliestEventTime = earliestEventTime;
	}
	public Long getEarliestEventTime() {
		return earliestEventTime;
	}

	public boolean isIncludeImageData() {
		return includeImageData;
	}

	public void setIncludeImageData(boolean includeImageData) {
		this.includeImageData = includeImageData;
	}

	public LatLng getSouthWest() {
		return southWest;
	}

	public void setSouthWest(double latitude, double longitude) {
		this.southWest = new LatLng(latitude, longitude);
	}

	public LatLng getNorthEast() {
		return northEast;
	}

	public void setNorthEast(double latitude, double longitude) {
		this.northEast = new LatLng(latitude, longitude);
	}
	
	public void setBoundsFromPoint( double latitude, double longitude, int radius){
		int offSet = radius/2;
		this.setSouthWest(latitude - offSet, longitude - offSet);
		this.setNorthEast(latitude + offSet, longitude + offSet);
	}
	
}
