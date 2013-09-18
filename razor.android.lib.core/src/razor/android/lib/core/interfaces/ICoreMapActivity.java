package razor.android.lib.core.interfaces;

import com.google.android.maps.GeoPoint;


public interface ICoreMapActivity {
	void setMapContentView();
	IMapOverlayWrapper createMapOverlay();
	void addOverlay(IMapOverlayWrapper overlay);
	void clearOverlays();
	void centerMap(double latitude, double longitude);
	void centerMap(GeoPoint point);
	void zoomToSpan(int latSpan, int longSpan);
	
}
