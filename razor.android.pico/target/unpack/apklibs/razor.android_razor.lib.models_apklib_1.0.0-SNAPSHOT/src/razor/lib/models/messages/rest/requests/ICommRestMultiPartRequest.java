package razor.lib.models.messages.rest.requests;

import java.util.List;

import org.apache.http.NameValuePair;

public interface ICommRestMultiPartRequest extends ICommRestRequest{
	List<NameValuePair> getNameValuePairs();
}
