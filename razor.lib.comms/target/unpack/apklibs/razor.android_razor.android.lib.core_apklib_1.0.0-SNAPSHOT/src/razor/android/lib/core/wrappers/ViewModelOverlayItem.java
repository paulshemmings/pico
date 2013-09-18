package razor.android.lib.core.wrappers;

import razor.android.lib.core.interfaces.ICoreViewModel;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public final class ViewModelOverlayItem extends OverlayItem {

	public ViewModelOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}
	
	public ViewModelOverlayItem(GeoPoint point, ICoreViewModel model) {
		super(point, model.getTitle(), model.getDetails() == null || model.getDetails().length <1 ? "" : model.getDetails()[0]);
		this.setModel(model);
	}	
	
	private ICoreViewModel model = null;
	public ICoreViewModel getModel(){return this.model;}
	public void setModel(ICoreViewModel model){this.model = model;}


}
