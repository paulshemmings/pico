package razor.android.lib.core.interfaces;

import razor.android.lib.core.wrappers.ViewModelOverlayItem;

import com.google.android.maps.GeoPoint;

public interface IMapOverlayWrapper {

	int addItems(ViewModelOverlayItem[] items);

	int addItem(ViewModelOverlayItem item);

	int addItem(double lat, double lon, ICoreViewModel model);

	void loadItems();

	int size();
	
	GeoPoint getOverlayCenter();
	
	int getOverlayLatitudeSpan();
	
	int getOverlayLongitudeSpan();	

}