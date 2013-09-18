package razor.lib.comms.rest.handlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.android.lib.core.helpers.LogHelper;
import razor.lib.models.messages.rest.requests.ICommRestMultiPartRequest;
import razor.lib.models.messages.rest.requests.ICommRestRequest;

public class MultiElementJsonRestHandler extends AbstractRestHandler implements ICommRestHandler {

	@Override
	protected HttpEntity buildEntity(ICommRestRequest restRequest) throws Exception{
		ICommRestMultiPartRequest multiPartRequest = null;		
		if( restRequest instanceof ICommRestMultiPartRequest){
			multiPartRequest = (ICommRestMultiPartRequest) restRequest;
		}	
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT);
        if(multiPartRequest != null){
	        for(NameValuePair nvp:multiPartRequest.getNameValuePairs()) {
	            if(nvp.getName().equalsIgnoreCase("File")) { 
	                File file = new File(nvp.getValue());
	                FileBody isb = new FileBody(file,"application/*");
	                entity.addPart(nvp.getName(), isb);
	            } else {
	            	try{
	            		LogHelper.w("RestRequest-Request",nvp.getName() + ": " + nvp.getValue());
		            	ContentBody cb =  new StringBody(nvp.getValue(),"", null);
		                entity.addPart(nvp.getName(),cb);
	            	}catch(Exception ex){
	            		LogHelper.e("MultiElementJsonRestHandler", "failed to add name value pair to HttpEntity with " + ex.getMessage());
	            	}
	            } 
	        } 
        }
        return entity;
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
