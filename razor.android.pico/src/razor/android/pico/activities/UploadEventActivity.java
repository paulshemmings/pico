package razor.android.pico.activities;

import java.io.File;
import java.io.IOException;
import java.util.List;

import razor.android.pico.R;
import razor.android.pico.handlers.EventHandler;
import razor.android.pico.handlers.EventHandler.onEventLoadedListener;
import razor.android.pico.holders.UploadEventViewHolder;
import razor.android.pico.holders.UploadEventViewHolder.UploadEventListener;
import razor.android.pico.viewmodels.EventViewModel;
import razor.android.lib.core.CoreTemplateActivity;
import razor.android.lib.core.holders.ThreeButtonTemplateViewHolder;
import razor.android.lib.core.holders.ThreeButtonTemplateViewHolder.OnThreeButtonTemplateEventListener;
import razor.lib.models.pico.EventDTO;
import razor.android.lib.core.wrappers.ImageWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class UploadEventActivity extends CoreTemplateActivity implements UploadEventListener, onEventLoadedListener, OnThreeButtonTemplateEventListener {

	private static final int RES_IMAGE_CAPTURE = 1010101;
	private UploadEventViewHolder viewHolder = null;
	private ThreeButtonTemplateViewHolder buttonHolder = null;
	private EventViewModel eventViewModel = null;
	
	/**
	 * Create
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        super.setTemplateContentView(razor.android.lib.core.R.layout.lib_three_button_template_layout, razor.android.lib.core.R.id.lib_three_button_template_content, R.layout.upload_event_layout);
        
        this.viewHolder = new UploadEventViewHolder(this, this.getTemplateContentView());
        this.buttonHolder = new ThreeButtonTemplateViewHolder(this,this.getTemplateContentView());
        
        this.buttonHolder.setNegative("back to map");
        this.buttonHolder.setNeutral("capture");
        this.buttonHolder.setPositive("store");
        
        if(savedInstanceState==null){
        	this.eventViewModel = new EventViewModel();
        	this.eventViewModel.setModel(new EventDTO());
        }else{
        	this.eventViewModel = (EventViewModel) savedInstanceState.getSerializable("eventViewModel");
        	
        	try {
				this.viewHolder.setViewModel(this, this.eventViewModel);
			} catch (Exception e) {
				e.printStackTrace();
			}        	
        }
    }
    
    /**
     * Save instance state when it goes to the preview screen
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    
    public void onSaveInstanceState(Bundle state) {
		state.putSerializable("eventViewModel", this.eventViewModel);
    }
    
    /**
     * Take photo
     */
    
	public void takePhoto() {

		// create path for the saved image

		String captured_image = "pico_" + System.currentTimeMillis() + ".jpg";
		File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), captured_image);
		
	    Uri outputFileUri = Uri.fromFile(file); 
	    this.eventViewModel.setFullSizedImagePath(outputFileUri.getPath());
	    
	    // store the info they added to the page 
	    this.eventViewModel.getModel().setTags(this.viewHolder.getViewModel().getModel().getTags());
	    this.eventViewModel.getModel().setTitle(this.viewHolder.getViewModel().getModel().getTitle());
		
	    // build the intent, and start activity
	    
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri); 
	    intent.putExtra("return-data", true);
	    this.startActivityForResult(intent, RES_IMAGE_CAPTURE);
	}
	
	/**
	 * photo activity complete
	 */
	
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
	                		this.eventViewModel.setImagePath(this.resizeImage());
	                		this.viewHolder.setViewModel(this, this.eventViewModel);	  
	                		this.buttonHolder.resetActionButtons();
	                		
		                }catch(Exception ignore){}
	                    break;
	                }
	            break;

	    }   
	}
	
	/**
	 * resize the image and store in single location
	 * @return
	 * @throws IOException
	 */
	
	private String resizeImage() throws IOException{
		
		ImageWrapper imageWrapper = new ImageWrapper();
		String fullSizedImagePath = this.eventViewModel.getFullSizedImagePath();
		int newHeight = this.getResizedImageHeight();
		String resizedImagePath = imageWrapper.resizeImage(this, fullSizedImagePath, "pico_resized_image.JPG", newHeight);
		return resizedImagePath;
	}
	
    /**
     * get resized image height
     */
    
    private int getResizedImageHeight(){
    	String imageHeightString = this.getResources().getString(R.string.resized_image_height);
    	return Integer.valueOf(imageHeightString).intValue();
    }	
	
	/**
	 * the new event was stored successfully
	 */


	@Override
	public void onEventLoaded() {
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				UploadEventActivity.this.showProgressDialog(false);
				Toast.makeText(UploadEventActivity.this, "success", 1000).show();
				UploadEventActivity.this.viewHolder.reset();
				UploadEventActivity.this.buttonHolder.resetActionButtons();
			}			
		});		
	}
	
	/**
	 * the new event failed to store
	 */

	@Override
	public void onEventLoadFailed(final String errorMessage) {
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {		
				UploadEventActivity.this.showProgressDialog(false);
				Toast.makeText(UploadEventActivity.this, errorMessage, 1000).show();
				UploadEventActivity.this.buttonHolder.resetActionButtons();
			}
		});
	}
	
	/**
	 * store the event
	 */


	@Override
	public void onThreeButtonPositive() {
		EventViewModel viewModel = this.viewHolder.getViewModel();
		if( !viewModel.getImagePath().isEmpty() ){
			this.showProgressDialog(true,"uploading picture");
			EventHandler handler = new EventHandler(this);
			handler.uploadEvent(this, viewModel.getImagePath(), viewModel.getModel().getTags(), viewModel.getModel().getTitle() );
		}
	}
	
	/**
	 * capture the image
	 */


	@Override
	public void onThreeButtonNeutral() {
		this.takePhoto();
	}
	
	/**
	 * cancel the new event request. leave the page
	 */


	@Override
	public void onThreeButtonNegative() {
		this.finish();
	}
}
