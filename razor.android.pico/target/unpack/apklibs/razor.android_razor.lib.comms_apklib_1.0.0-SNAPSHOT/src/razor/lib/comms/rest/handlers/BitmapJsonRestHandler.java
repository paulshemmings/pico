package razor.lib.comms.rest.handlers;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.conn.EofSensorInputStream;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.android.lib.core.helpers.LogHelper;
import razor.lib.models.messages.rest.requests.ICommRestJsonRequest;
import razor.lib.models.messages.rest.requests.ICommRestRequest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapJsonRestHandler extends AbstractRestHandler implements ICommRestHandler {

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
			// deconstruct the response entity		
	        HttpEntity entity = response.getEntity();        
	        // debug
	        System.out.println(entity.getContentType());
	        System.out.println(entity.getContentLength());
	        LogHelper.w("RestRequest-Response", "content length %d", entity.getContentLength());
	        System.out.println(EntityUtils.getContentCharSet(entity));
	        // get input stream
	        org.apache.http.conn.EofSensorInputStream inputStream = (EofSensorInputStream) entity.getContent();
	        // get image from input stream
	        Bitmap image = BitmapFactory.decodeStream(inputStream);
			// add to response
			ICommRestResponse<Bitmap> streamResponse = restResponse;
			streamResponse.setResponseContent(image);
			// set success
			restResponse.setResponseCode( (long) (image == null ? 999 : 0));
		}catch(Exception ex){
			restResponse.setErrorMessage(ex.getMessage());
			restResponse.setResponseCode((long) 999);
		}
	}

}
