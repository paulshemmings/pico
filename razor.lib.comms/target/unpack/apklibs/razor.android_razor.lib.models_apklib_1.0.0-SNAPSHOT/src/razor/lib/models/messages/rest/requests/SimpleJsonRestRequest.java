package razor.lib.models.messages.rest.requests;

import razor.lib.models.messages.ICommRequest;

import com.google.gson.Gson;

public class SimpleJsonRestRequest extends SimpleRestRequest implements ICommRestJsonRequest{

	protected ICommRequest request;

	public SimpleJsonRestRequest(ICommRequest request, String commandPath) {
		super(commandPath);
		this.request = request;
	}

	@Override
	public String getJSON() {		
		String json = null;
		try{
			Gson gson = new Gson();
			json = gson.toJson(this.request);
		}catch(Exception ex){
			// LogHelper.e(TAG,"getJSON failed with %s",ex.getMessage());
		}
		return json;
	}
		
	public int hashCode(){
		int hashCode = 1;
		hashCode = hashCode & (this.getCommand()== null ? hashCode : this.getCommand().hashCode());
		hashCode = hashCode & (this.getJSON() == null ? hashCode : this.getJSON().hashCode());
		return hashCode;
	}
	
	public boolean equals(Object obj){
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        SimpleJsonRestRequest rhs = (SimpleJsonRestRequest) obj;
        return
        	(this.getCommand() == null && rhs.getCommand() == null && this.getJSON() == null && rhs.getJSON() == null)
        	||
        	(this.getCommand().equals(rhs.getCommand()) && this.getJSON().equals(rhs.getJSON()));
	}	
	
}
