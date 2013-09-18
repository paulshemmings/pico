package razor.android.pico.activities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import razor.android.pico.R;
import razor.android.pico.handlers.EventHandler;
import razor.android.pico.handlers.EventHandler.onEventLoadedListener;
import razor.android.pico.holders.CaptureImageViewHolder;
import razor.android.pico.holders.CaptureImageViewHolder.FrontPageEventListener;
import razor.android.lib.core.CoreActivity;
import razor.android.lib.core.wrappers.ImageWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

public class CaptureImageActivity extends CoreActivity implements FrontPageEventListener, onEventLoadedListener {
    
	private static final int RES_IMAGE_CAPTURE = 1010101;
	private CaptureImageViewHolder viewHolder = null;
	private String outputFileUriPath = null;
	
	/**
	 * Create. Retrieve saved file Uri path
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capture_image_layout);
        
        View mainView = this.findViewById(android.R.id.content);
        this.viewHolder = new CaptureImageViewHolder(this, mainView);
        
        if(savedInstanceState!=null){
        	this.outputFileUriPath = savedInstanceState.getString("outputFileUriPath");
        }
    }
    
    /**
     * Save instance state when it goes to the preview screen
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    
    public void onSaveInstanceState(Bundle state) {
    	if(this.outputFileUriPath!=null){
    		state.putString("outputFileUriPath", this.outputFileUriPath);
    	}
    }
	
	/**
	 * Take photo
	 */
	
	@Override
	public void onTakePhotoRequested() {

		// create path for the saved image

		String captured_image = System.currentTimeMillis() + ".jpg";
	    File file = new File(Environment.getExternalStorageDirectory(), captured_image); 
	    Uri outputFileUri = Uri.fromFile(file); 
	    this.outputFileUriPath = outputFileUri.getPath();
		
	    // build the intent, and start activity
	    
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri); 
	    intent.putExtra("return-data", true);
	    this.startActivityForResult(intent, RES_IMAGE_CAPTURE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
	    switch (requestCode) { 
	        case RES_IMAGE_CAPTURE: 
	            switch( resultCode )
	            {
	                case 0:
	                    break;
	                case -1:
	                	try{
	                		// resize the photo
	                		String resizedImagePath = this.resizeImage();
	                		this.viewHolder.showRecordedImage(this, resizedImagePath);
	                		
	                		Intent newIntent = new Intent(CaptureImageActivity.this, UploadEventActivity.class);
	                		//newIntent.putExtra(UploadEventActivity.UPLOAD_IMAGE_PATH_INTENT, resizedImagePath);
	                		
	                		CaptureImageActivity.this.startActivity(newIntent);
	                		
	                		// upload the image
	                		/*
	                		EventHandler handler = new EventHandler(this);
	                		handler.uploadEvent(this, resizedImagePath, "", "");
	                		*/
		                }catch(Exception ignore){}
	                    break;
	                }
	            break;

	    }   
	}
	
	private String resizeImage() throws IOException{
		
		ImageWrapper imageWrapper = new ImageWrapper();
		String resizedImagePath = imageWrapper.resizeImage(this, this.outputFileUriPath, "pico_resized_image.JPG", 200);
		return resizedImagePath;
	}

	@Override
	public void onEventLoaded() {
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				Toast.makeText(CaptureImageActivity.this, "success", 1000).show();
			}			
		});		
	}

	@Override
	public void onEventLoadFailed(final String errorMessage) {
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {		
				Toast.makeText(CaptureImageActivity.this, errorMessage, 1000).show();
			}
		});
	}

	@Override
	public void onListLocalEventsRequested() {
		Intent i = new Intent(this,EventListActivity.class);
		this.startActivity(i);
	}

	@Override
	public void onShowLocalEventsRequested() {
		Intent i = new Intent(this,EventMapActivity.class);
		this.startActivity(i);	
	}

	
}