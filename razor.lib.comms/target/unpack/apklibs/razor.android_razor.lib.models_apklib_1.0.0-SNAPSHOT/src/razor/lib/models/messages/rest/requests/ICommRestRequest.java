package razor.lib.models.messages.rest.requests;

import razor.lib.models.messages.ICommRequest;

public interface ICommRestRequest  extends ICommRequest {
	boolean isPost();
	String getCommand();
	void setCommand(String command);
}
