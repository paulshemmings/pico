package razor.lib.models.messages;

import com.google.gson.annotations.SerializedName;

public class StatusReply implements ICommReply {
	
	@SerializedName("MessageType")
	private int messageType;
	
	@SerializedName("ErrorMessage") 
	private String errorMessage;
	
	@SerializedName("ResponseCode") 
	private long responseCode;
	
	public StatusReply(){}
	
	public StatusReply(int messageType){
		this.messageType = messageType;
	}
	
	public boolean isSuccessful(){
		return this.responseCode == 0;
	}

	public int getMessageType() {
		return messageType;
	}

	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public long getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(long responseCode) {
		this.responseCode = responseCode;
	}
	
	
}
