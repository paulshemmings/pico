package razor.lib.comms.rest.handlers;


import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.lib.models.messages.rest.requests.ICommRestJsonRequest;
import razor.lib.models.messages.rest.requests.ICommRestRequest;

public class StreamingJsonRestHandler extends AbstractRestHandler implements ICommRestHandler {
	
	@Override
	protected HttpEntity buildEntity(ICommRestRequest restRequest) throws Exception{
		StringEntity se = null;
		ICommRestJsonRequest jsonRequest = (ICommRestJsonRequest) restRequest;
		String json = jsonRequest.getJSON();
		se = new StringEntity(json, HTTP.UTF_8);
		se.setContentType("application/json; charset=utf-8");
		return se;
	}

	@Override
	protected void storeResponse(HttpResponse response, ICommRestResponse restResponse) throws Exception{
		// deconstruct the response entity
		
        HttpEntity entity = response.getEntity();        
        // debug
        System.out.println(entity.getContentType());
        System.out.println(entity.getContentLength());
        System.out.println(EntityUtils.getContentCharSet(entity));
        // get input stream
        org.apache.http.conn.EofSensorInputStream inputStream = (EofSensorInputStream) entity.getContent();		
		// add to response
		ICommRestResponse<InputStream> streamResponse = restResponse;
		streamResponse.setResponseContent(inputStream);
	}


}
