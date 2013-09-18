package razor.lib.comms.rest.handlers;

import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import razor.lib.comms.ICommsRestProvider;
import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.android.lib.core.helpers.LogHelper;
import razor.lib.models.messages.rest.requests.ICommRestRequest;

public abstract class AbstractRestHandler {
	
	protected abstract HttpEntity buildEntity(ICommRestRequest restRequest) throws Exception;
	protected abstract void storeResponse(HttpResponse response, ICommRestResponse restResponse) throws Exception;

	public void populateRestResponse(ICommsRestProvider restProvider, ICommRestRequest restRequest, ICommRestResponse restResponse) {

	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    httpClient.setParams(this.buildParameters());	    
        HttpResponse res;
        try {
        	
        	String url = String.format("%s%s",restProvider.getUrl(),restRequest.getCommand()); 
        	LogHelper.i("RestRequest-Target", url);
        	
        	if(restRequest.isPost()){
	
	            HttpPost httpost = new HttpPost(url);
	            HttpEntity httpEntity = this.buildEntity(restRequest);
	            httpost.setEntity(httpEntity);
	            
	            HttpResponse response = httpClient.execute(httpost);
	            this.storeResponse(response, restResponse);
	            
	        }else{
	        	
	        	HttpGet httpGet = new HttpGet(url);
	        	HttpResponse response = httpClient.execute(httpGet);
	            this.storeResponse(response, restResponse);
	            
	        }                    
            
        } catch(Exception ex){
        	String message = ex.getMessage() == null ? "general failure" : ex.getMessage();
        	LogHelper.e("AbstractRestHandler", message);
        	restResponse.setErrorMessage(message);
        	restResponse.setResponseCode((long) 999);
        }
	}
	
	/**
	 * Return connection timeout duration
	 * @return int
	 */
	
	private int getConnectionTimeoutDuration(){
		return 30000;
	}	
	
	/**
	 * Return socket timeout duration
	 * @return int
	 */
	
	private int getSocketTimeoutDuration(){
		return 60000;
	}
	
	/**
	 * build parameters for REST call
	 * @return
	 */
	
	private HttpParams buildParameters(){
		HttpParams params = new BasicHttpParams();			
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		//params.setParameter(ClientPNames.HANDLE_REDIRECTS, new Boolean(false));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
					
		HttpProtocolParams.setUseExpectContinue(params, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		
		HttpConnectionParams.setConnectionTimeout(params, getConnectionTimeoutDuration());
		HttpConnectionParams.setSoTimeout(params, getSocketTimeoutDuration());		
		return params;
	}
}
