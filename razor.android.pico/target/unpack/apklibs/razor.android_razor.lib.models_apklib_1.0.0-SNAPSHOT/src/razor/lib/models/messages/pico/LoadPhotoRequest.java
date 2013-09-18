package razor.lib.models.messages.pico;

import razor.lib.models.messages.rest.requests.ICommRestRequest;
import razor.lib.models.messages.rest.requests.SimpleRestRequest;

import com.google.gson.annotations.SerializedName;

public class LoadPhotoRequest extends SimpleRestRequest implements ICommRestRequest{

	@SerializedName("id") 
	private Long photoId;
	
	public LoadPhotoRequest(){
		super(null);
		this.isPost = false;
	}

	public void setPhotoId(Long photoId) {
		this.photoId = photoId;
	}

	public Long getPhotoId() {
		return photoId;
	}
	
	/**
	 * Use this to pass a GET inline request instead of a POST
	 * request where the parameters are a JSON string in the content
	 * of the request. If you want to pass this as a POST with the parameters
	 * in the content, then simply remove this method, and set the isPost boolean
	 * to true.... and then change this so it doesn't extend SimpleRestRequest
	 * and then change the CommsProvider to create a SimpleRestRequest from this.
	 * Otherwise you'll get all the additional data in the JSON object which we won't
	 * need.
	 */
	
	@Override
	public String getCommand() {
		return String.format("%s?id=%d&t=%d", this.commandPath, this.photoId, System.currentTimeMillis());
	}
	
}
