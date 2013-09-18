package razor.lib.comms.rest.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.android.lib.core.helpers.LogHelper;
import razor.lib.models.messages.rest.requests.ICommRestJsonRequest;
import razor.lib.models.messages.rest.requests.ICommRestRequest;

public class SimpleJsonRestHandler extends AbstractRestHandler implements ICommRestHandler {

	@Override
	protected HttpEntity buildEntity(ICommRestRequest restRequest) throws Exception{
		StringEntity se = null;
		ICommRestJsonRequest jsonRequest = (ICommRestJsonRequest) restRequest;
		String json = jsonRequest.getJSON();
		LogHelper.w("RestRequest-Entity", json);
		
		se = new StringEntity(json, HTTP.UTF_8);
		se.setContentType("application/json; charset=utf-8");
		return se;
	}

	@Override
	protected void storeResponse(HttpResponse response, ICommRestResponse restResponse) throws Exception{
		try{
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}	
			restResponse.setResponseContent(sb.toString());
			LogHelper.w("RestRequest-Response", (String) restResponse.getResponseContent());
			restResponse.setResponseCode((long)( sb == null || sb.toString().length()==0 ? 999 : 0 ));
		}catch(Exception ex){
			restResponse.setResponseCode((long)999);
			restResponse.setErrorMessage(ex.getMessage());
		}
	}


}
