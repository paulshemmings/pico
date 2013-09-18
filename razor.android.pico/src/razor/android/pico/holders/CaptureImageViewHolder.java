package razor.android.pico.holders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import razor.android.pico.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class CaptureImageViewHolder implements OnClickListener {

	public interface FrontPageEventListener{
		void onTakePhotoRequested();
		void onListLocalEventsRequested();
		void onShowLocalEventsRequested();
	}
	
	private FrontPageEventListener eventListener;
	private Button takePhotoAction;
	private Button listLocalEvents;
	private Button showLocalEvents;
	private ImageView eventImageView;
	
	public CaptureImageViewHolder(FrontPageEventListener listener, View parentView){
		this.eventListener = listener;		
		
		this.takePhotoAction = (Button) parentView.findViewById(R.id.front_page_take_photo_action);
		this.listLocalEvents = (Button) parentView.findViewById(R.id.list_local_events);
		this.showLocalEvents = (Button) parentView.findViewById(R.id.show_local_events);
		this.eventImageView = (ImageView) parentView.findViewById(R.id.capture_image_image_view);
		
		this.takePhotoAction.setOnClickListener(this);
		this.listLocalEvents.setOnClickListener(this);
		this.showLocalEvents.setOnClickListener(this);
	}
	
	public void showRecordedImage(Context context, String imagePath) throws FileNotFoundException{
		//File file = new File(Environment.getExternalStorageDirectory(), imagePath); 		
		//Uri outputFileUri = Uri.fromFile(file);
		
		Uri outputFileUri = Uri.fromFile(new File(imagePath));
		this.eventImageView.setImageURI(outputFileUri);
	}	

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.front_page_take_photo_action:
			this.eventListener.onTakePhotoRequested();
			break;
		case R.id.list_local_events:
			this.eventListener.onListLocalEventsRequested();
			break;
		case R.id.show_local_events:
			this.eventListener.onShowLocalEventsRequested();
		}
	}
}
