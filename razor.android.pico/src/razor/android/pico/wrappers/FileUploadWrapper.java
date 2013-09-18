package razor.android.pico.wrappers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.ByteArrayBuffer;

import razor.android.lib.core.helpers.LogHelper;

import android.content.Context;
import android.provider.Settings.Secure;

public class FileUploadWrapper {

	/**
	 * http://blog.tacticalnuclearstrike.com/2010/01/using-multipartentity-in-android-applications/
	 * http://stackoverflow.com/questions/6042416/build-a-photo-upload-application-in-android
	 * http://vikaskanani.wordpress.com/2011/01/29/android-image-upload-activity/
	 * http://stackoverflow.com/questions/8006488/how-to-upload-image-from-device-to-server-how-to-show-on-a-progress-bar-how-mu
	 * http://stackoverflow.com/questions/11242989/i-cant-access-my-apache-server-from-my-android-device
	 */
	
	private HttpParams buildParameters(){
		HttpParams params = new BasicHttpParams();			
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		//params.setParameter(ClientPNames.HANDLE_REDIRECTS, new Boolean(false));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
					
		HttpProtocolParams.setUseExpectContinue(params, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		
		HttpConnectionParams.setConnectionTimeout(params, 30000);
		HttpConnectionParams.setSoTimeout(params, 30000);		
		return params;
	}
	
	public String downloadImage(HttpResponse response, String encoding) throws IllegalStateException, IOException{
        InputStream is = response.getEntity().getContent();
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayBuffer baf = new ByteArrayBuffer(50);
        int current = 0;
        while((current = bis.read()) != -1){
              baf.append((byte)current);
         }
        return new String(baf.toByteArray(),encoding);		
	}
	
	public void uploadImage(Context context, String url, List<NameValuePair> nameValuePairs) {

	    DefaultHttpClient httpClient = new DefaultHttpClient();
	    httpClient.setParams(this.buildParameters());

	    /*
        SSLSocketFactory ssl =  (SSLSocketFactory)httpClient.getConnectionManager().getSchemeRegistry().getScheme( "https" ).getSocketFactory(); 
        ssl.setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER );
        final String username = "username";
        final String password = "password";
        UsernamePasswordCredentials c = new UsernamePasswordCredentials(username,password);
        BasicCredentialsProvider cP = new BasicCredentialsProvider(); 
        cP.setCredentials(AuthScope.ANY, c); 
        httpClient.setCredentialsProvider(cP);
        */
	    
        HttpResponse res;
        try {
            HttpPost httpost = new HttpPost(url);
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.STRICT); 

            for(int index=0; index < nameValuePairs.size(); index++) { 
                ContentBody cb;
                if(nameValuePairs.get(index).getName().equalsIgnoreCase("File")) { 
                    File file = new File(nameValuePairs.get(index).getValue());
                    FileBody isb = new FileBody(file,"application/*");
                    entity.addPart(nameValuePairs.get(index).getName(), isb);
                } else { 
                    // Normal string data 
                    cb =  new StringBody(nameValuePairs.get(index).getValue(),"", null);
                    entity.addPart(nameValuePairs.get(index).getName(),cb); 
                } 
            } 
            
            String androidId = Secure.getString(context.getContentResolver(),Secure.ANDROID_ID); 
            entity.addPart("DeviceId",new StringBody(androidId,"",null));

            httpost.setEntity(entity);
            res = httpClient.execute(httpost);
            
	        res = null;
	        httpost = null;            
        }         
        catch (ClientProtocolException e) {
        	LogHelper.e("FileUploadWrapper.uploadFile", e.getMessage());
        } 
        catch (IOException e) {
        	LogHelper.e("FileUploadWrapper.uploadFile", e.getMessage());
        } 
        finally{
        }
	}
}
