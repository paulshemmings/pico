package razor.android.pico.adapters;

import java.util.List;

import razor.android.pico.R;
import razor.android.pico.viewmodels.EventViewModel;
import razor.android.lib.core.adapters.CoreViewModelListAdapter;
import razor.android.lib.core.interfaces.ICoreViewModel;
import razor.android.lib.core.wrappers.ImageWrapper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EventListAdapter extends CoreViewModelListAdapter {

	/**
	 * View Adapter for InsitutionViewModel in list view 
	 * @author phemmings 
	 */
	
	public EventListAdapter(Context context, List<ICoreViewModel> objects) {
		super(context, R.layout.event_list_item_layout, objects);
		//super(context, objects);
	}

	public void renderRow(View row, ICoreViewModel model){
		if(model != null){
			EventViewModel eventViewModel = (EventViewModel) model;
			
			TextView tv = (TextView) row.findViewById(this.getMainTextId());
			tv.setText(model.getTitle());
	
			TextView stv = (TextView) row.findViewById(this.getSubTextId());
			stv.setText(eventViewModel.getModel().getCreateDateTime().toString());

			if(eventViewModel.getModel().getImageData()!=null){
				
				byte[] bitmapdata = eventViewModel.getModel().getImageData();
				Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
				Bitmap icon = new ImageWrapper().resizeImage(bitmap, 150);
				
				ImageView eventImageView = (ImageView) row.findViewById(R.id.event_image_view);
				eventImageView.setImageBitmap(icon);	
			}
			row.setTag(model);		
		}		
	}	
	
}
