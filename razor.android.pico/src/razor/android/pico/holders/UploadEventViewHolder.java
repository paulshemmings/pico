package razor.android.pico.holders;

import java.io.File;
import java.io.FileNotFoundException;

import razor.android.pico.R;
import razor.android.pico.viewmodels.EventViewModel;
import razor.lib.models.pico.EventDTO;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class UploadEventViewHolder implements OnClickListener{

	public interface UploadEventListener{
	}	
	
	private UploadEventListener eventListener;
	private ImageView eventImage;
	private EditText eventTitleText;
	private EditText eventTagsText;
	private EventViewModel vm = new EventViewModel();
	
	public UploadEventViewHolder(UploadEventListener listener, View parentView){
		this.eventListener = listener;		
		
		this.eventImage = (ImageView) parentView.findViewById(R.id.upload_event_image_view);
		this.eventTitleText = (EditText) parentView.findViewById(R.id.upload_event_title);
		this.eventTagsText = (EditText) parentView.findViewById(R.id.upload_event_tags);
		this.reset();
	}
	
	private void showRecordedImage(Context context, String imagePath) throws FileNotFoundException{	
		if(imagePath!=null){	
			Uri outputFileUri = Uri.fromFile(new File(imagePath));
			this.eventImage.setImageURI(outputFileUri);
			this.eventImage.setScaleType(ScaleType.FIT_XY);
		}
	}		
	
	private void showPlaceholder(){
		this.eventImage.setImageResource(R.drawable.lib_image_placeholder);
		this.eventImage.setScaleType(ScaleType.FIT_XY);
	}
	
	public void reset(){
		this.eventTagsText.setText("");
		this.eventTitleText.setText("");
		this.showPlaceholder();
	}
	
	public void setViewModel(Context context, EventViewModel viewModel) throws FileNotFoundException{
		this.vm = viewModel;
		
		if(this.vm.getModel()!=null){
			this.eventTagsText.setText(this.vm.getModel().getTags());
			this.eventTitleText.setText(this.vm.getModel().getTitle());
		}
		
		this.showRecordedImage(context, this.vm.getImagePath());
	}
	
	public EventViewModel getViewModel(){
		this.vm.setModel(new EventDTO());
		this.vm.getModel().setTitle(this.eventTitleText.getText().toString());
		this.vm.getModel().setTags(this.eventTagsText.getText().toString());	
		return this.vm;
	}
		
	@Override
	public void onClick(View view) {
	}

}
