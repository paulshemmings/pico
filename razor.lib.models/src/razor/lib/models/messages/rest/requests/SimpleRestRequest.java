package razor.lib.models.messages.rest.requests;


public abstract class SimpleRestRequest implements ICommRestRequest {

	protected boolean isPost = true;
	protected String commandPath = null;
	protected static final String TAG = "SimpleRestRequest";
	
	public SimpleRestRequest(String commandPath) {
		this.commandPath = commandPath;
	}
	
	@Override
	public String getCommand() {
		return this.commandPath;
	}
	
	@Override
	public void setCommand(String command) {
		this.commandPath = command;
	}

	public boolean isPost() {
		return isPost;
	}

	public void setPost(boolean isPost) {
		this.isPost = isPost;
	}	
	
	

}
