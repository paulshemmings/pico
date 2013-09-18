package razor.android.lib.core;

import razor.android.lib.core.helpers.LayoutHelper;
import razor.android.lib.core.interfaces.ICoreMapActivity;
import razor.android.lib.core.interfaces.IMapOverlayWrapper;
import razor.android.lib.core.wrappers.LatLongPoint;
import razor.android.lib.core.wrappers.MapOverlayWrapper;
import razor.android.lib.core.wrappers.MapOverlayWrapper.onMapOverlayEventListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;

public abstract class CoreMapActivity extends MapActivity implements ICoreMapActivity, onMapOverlayEventListener {
	
	protected MapView mapView = null;
	protected MyLocationOverlay locationOverlay = null;
	protected Drawable genericMarker = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {         
        super.onCreate(savedInstanceState);
    }
    
    public void setMapContentView(){
    	this.setMapContentView(R.layout.lib_map_template, R.id.lib_map_template_map_view);
    }
    
    public void setMapContentView(int layoutID, int mapWidgetID){
    	
        setContentView(layoutID);
         
        this.mapView = (MapView) findViewById(mapWidgetID);      
        this.mapView.setBuiltInZoomControls(true);
                
        this.genericMarker = getResources().getDrawable(razor.android.lib.core.R.drawable.lib_red_map_marker);
        this.genericMarker.setBounds(0, 0, this.genericMarker.getIntrinsicWidth(),this.genericMarker.getIntrinsicHeight());

		this.locationOverlay = new MyLocationOverlay(this, this.mapView);
		mapView.getOverlays().add(this.locationOverlay);        
    }	
    
    public void centerMap(double latitude, double longitude){
		GeoPoint point = new LatLongPoint(latitude,longitude);
		mapView.getController().animateTo(point);
    }
    
    public void centerMap(GeoPoint point){
    	if(point!=null){
    		mapView.getController().animateTo(point);
    	}
    }   
    
    public void zoomToSpan(int latSpan, int longSpan){
    	mapView.getController().zoomToSpan(latSpan, longSpan);
    }
    
    public void setBottomStubView(int layoutID){
    	LayoutHelper.setStubView(this, razor.android.lib.core.R.id.lib_template_bottom_stub, layoutID);
    }
    
    public void setTopStubView(int layoutID){
    	LayoutHelper.setStubView(this, razor.android.lib.core.R.id.lib_template_top_stub, layoutID);
    }    

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public IMapOverlayWrapper createMapOverlay(){
		return new MapOverlayWrapper(this,this.genericMarker,10);
	}
	
	public void addOverlay(IMapOverlayWrapper overlay){
		this.mapView.getOverlays().add((Overlay) overlay);
	}
	
	public void clearOverlays(){
		this.mapView.getOverlays().clear();
	}

}
