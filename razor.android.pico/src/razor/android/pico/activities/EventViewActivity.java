package razor.android.pico.activities;

import java.io.FileNotFoundException;
import java.io.IOException;

import razor.android.pico.R;
import razor.android.pico.handlers.PhotoHandler;
import razor.android.pico.handlers.PhotoHandler.onLoadPhotoListener;
import razor.android.pico.holders.UploadEventViewHolder;
import razor.android.pico.holders.UploadEventViewHolder.UploadEventListener;
import razor.android.pico.viewmodels.EventViewModel;
import razor.android.lib.core.CoreActivity;
import razor.android.lib.core.wrappers.ImageWrapper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

public class EventViewActivity extends CoreActivity implements UploadEventListener, onLoadPhotoListener {

	private UploadEventViewHolder viewHolder = null;
	private EventViewModel eventViewModel = null;
	
	public static final String EVENT_VIEW_MODEL_INTENT_VALUE = "EventViewModelIntentValue";
	/**
	 * create
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {        
    	super.onCreate(savedInstanceState);    	
    	setContentView(R.layout.upload_event_layout);
    	
    	View mainView = this.findViewById(android.R.id.content);
    	this.viewHolder = new UploadEventViewHolder(this,mainView);
    	
    	Object intentValue = this.getIntentSerializableValue(EVENT_VIEW_MODEL_INTENT_VALUE);
    	if(intentValue!=null){
    		this.showProgressDialog(true);
    		this.eventViewModel = (EventViewModel) intentValue;    		
    		PhotoHandler handler = new PhotoHandler(this);
    		handler.getPhoto(this, this.eventViewModel.getID());    		
    	}
    }
    
	@Override
	public void onPhotoLoaded(final Long eventId, final Bitmap bm)  {
		this.runOnUiThread(new Runnable(){
			public void run() {
				EventViewActivity.this.showProgressDialog(false);
				ImageWrapper wrapper = new ImageWrapper();
				try {
					String tmpFile = wrapper.saveBitmapAs(EventViewActivity.this, bm, "pico_resized_image.JPG");
					EventViewActivity.this.eventViewModel.setImagePath(tmpFile);
					EventViewActivity.this.viewHolder.setViewModel(EventViewActivity.this, EventViewActivity.this.eventViewModel);
					
				} catch (IOException e) {
					e.printStackTrace();
				}	
			}			
		});
	}
	
	@Override
	public void onPhotoFailed(Long eventId, String errorMessage) {
		try {
			EventViewActivity.this.showProgressDialog(false);
			this.viewHolder.setViewModel(this, this.eventViewModel);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
