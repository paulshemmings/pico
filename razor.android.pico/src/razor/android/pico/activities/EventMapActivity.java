package razor.android.pico.activities;

import java.util.ArrayList;
import java.util.List;

import razor.android.pico.R;
import razor.android.pico.handlers.EventHandler;
import razor.android.pico.viewmodels.EventViewModel;
import razor.android.lib.core.helpers.LogHelper;
import razor.android.lib.core.interfaces.ICoreViewModel;
import razor.android.lib.core.wrappers.ImageWrapper;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EventMapActivity 	extends 	FragmentActivity 
								implements 	EventHandler.onEventsListedListener, 
											OnMarkerClickListener, 
											LocationSource, 
											LocationListener, 
											OnMapLongClickListener, 
											OnInfoWindowClickListener  {

	private static final String TAG = "EventMapActivity";
	private GoogleMap googleMap;
	private OnLocationChangedListener locationListener;
	private LocationManager locationManager;
	private List<ICoreViewModel> events = new ArrayList<ICoreViewModel>();
	private ImageWrapper imageWrapper = new ImageWrapper();
	protected ProgressDialog progressDialog = null;

	/**
	 * Create
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {         
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_map_layout);

		Criteria locationCriteria = new Criteria();
		locationCriteria.setAccuracy(Criteria.ACCURACY_FINE);
		this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		this.locationManager.requestLocationUpdates(locationManager.getBestProvider(locationCriteria, true), 1L, 2F, this);	

		if( this.initialiseMap() ){
			/*
			List<ICoreViewModel> savedEvents = this.loadSavedEvents(savedInstanceState); 
			if(savedEvents != null && savedEvents.size() > 0){
				this.loadEventsOntoMap(savedEvents);
			}
			*/
		}			
	}  
	
	/**
	 * load saved state
	 */
	
	private List<ICoreViewModel> loadSavedEvents(Bundle savedInstanceState){
		List<ICoreViewModel> savedEvents = new ArrayList<ICoreViewModel>();
        if(savedInstanceState!=null){
        	Object[] eventArray = (Object[]) savedInstanceState.getSerializable("eventArray");
        	if(eventArray!=null && eventArray.length>0){
        		LogHelper.w(TAG, "restoring %d events",eventArray.length);
        		for(Object event:eventArray){
        			savedEvents.add((ICoreViewModel) event);
        		}	
        	}
        }		
        return savedEvents;
	}
	
    /**
     * Save instance state when it goes to the preview screen
     * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
     */
    
    public void onSaveInstanceState(Bundle state) {
    	if(this.events!=null){
    		LogHelper.w(TAG, "saving %d events", this.events.size());
    		state.putSerializable("eventArray", this.events.toArray());
    	}
    	if(this.googleMap!=null && this.googleMap.getMyLocation()!=null){
    		double currentLatitude = this.googleMap.getMyLocation().getLatitude();
    		double currentLongitude = this.googleMap.getMyLocation().getLongitude();
    		state.putSerializable("currentLatitude", currentLatitude);
    		state.putSerializable("currentLongitude", currentLongitude);
    	}
    }	

	/**
	 * pause location manager
	 * @see android.app.Activity#onPause()
	 */

	@Override
	public void onPause()
	{
		if(locationManager != null)
		{
			locationManager.removeUpdates(this);
		}
		super.onPause();
	}

	/**
	 * resume location manager
	 */

	@Override
	public void onResume()
	{
		super.onResume();

		if( initialiseMap()){
			if(locationManager != null)
			{
				this.googleMap.setMyLocationEnabled(true);
			}
		}
	}  
	
	/**
	 * set up the map if not already done so
	 */
	
	private boolean initialiseMap() {
	    if (this.googleMap == null) 
	    {
	        // Try to obtain the map from the SupportMapFragment.
			//MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.event_main_map);
	    	SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.event_main_map);
	        
			this.googleMap = mapFragment.getMap();
			
			if(this.googleMap!=null){
				this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);		
				this.googleMap.setMyLocationEnabled(true);	
				this.googleMap.setOnMarkerClickListener(this);		
				this.googleMap.setOnInfoWindowClickListener(this);
				this.googleMap.setOnMapLongClickListener(this);
				this.googleMap.setLocationSource(this);				
	        }
	    }
	    return this.googleMap!=null;
	}
	
	/**
	 * center to the markers
	 */
	
	private void centerToEvents(){		
		
		if(this.events!=null && this.events.size()>0){
			
			LogHelper.w(TAG, "zooming map to %d events", this.events.size());

			EventViewModel firstEvent = null;
			double  minLatitude = 0; 
			double  maxLatitude = 0; 
			double  minLongitude = 0; 
			double  maxLongitude = 0;

			for(ICoreViewModel vm:this.events){
				if(firstEvent==null){
					firstEvent = (EventViewModel) vm;
					minLatitude = firstEvent.getModel().getLatitude();
					maxLatitude = firstEvent.getModel().getLatitude();
					minLongitude = firstEvent.getModel().getLongitude();
					maxLongitude = firstEvent.getModel().getLongitude();
				}else{
					EventViewModel event = (EventViewModel) vm;
					double latitude = event.getModel().getLatitude();
					double longitude = event.getModel().getLongitude();
					
					if(latitude < minLatitude) minLatitude = latitude;
					if(latitude > maxLatitude) maxLatitude = latitude;
					if(longitude < minLongitude) minLongitude = longitude;
					if(longitude > maxLongitude) maxLongitude = longitude;
				}
			}
			
			LatLng minLocation = new LatLng(minLatitude,minLongitude);
			LatLng maxLocation = new LatLng(maxLatitude,maxLongitude);
			LatLngBounds bounds = new LatLngBounds(minLocation,maxLocation);
			this.googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,200));
		}else{
			LogHelper.e(TAG, "event list was empty or null");
		}
	}

	/**
	 * center to current location (but only if current location is outside the currently viewed area)
	 */

	private void centerToLocation(){    	
		Location location = this.googleMap.getMyLocation();
		if(location!=null){
			LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
			LatLngBounds bounds = this.googleMap.getProjection().getVisibleRegion().latLngBounds;	
			if(!bounds.contains(currentLocation))
			{
				// zoom to current location
				float currentZoom = this.googleMap.getCameraPosition().zoom;				
				float maximumZoom = TrackerApplication.instance().getMaximumMapZoom();
				float targetZoom = currentZoom > maximumZoom ? maximumZoom : currentZoom;
				CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, targetZoom);
				this.googleMap.animateCamera(cameraUpdate, new GoogleMap.CancelableCallback(){
					public void onCancel() {
					}
					public void onFinish() {
						EventMapActivity.this.requestEvents(false);
					}
					
				});
			}    	
		}        		
	}

	/**
	 * Load the events
	 */

	private void requestEvents(boolean includeImages){
		LogHelper.w(TAG, "requesting events from system");
		this.showProgressDialog(true);
		EventHandler handler = new EventHandler(this);
		LatLngBounds bounds = this.googleMap.getProjection().getVisibleRegion().latLngBounds;
		handler.listEvents(this, includeImages, bounds);    	
	}

	/**
	 * On events loaded
	 */

	@Override
	public void onEventItemsLoaded(final List<ICoreViewModel> eventItems) {
		if(eventItems!=null){
			LogHelper.w(TAG, "loaded %d new events", eventItems.size());
			this.runOnUiThread(new Runnable(){
				public void run() {
					EventMapActivity.this.showProgressDialog(false);
					EventMapActivity.this.loadEventsOntoMap(eventItems);
				}				
			});
		}
	}  	

	/**
	 * event load failed
	 */

	@Override
	public void onEventItemsLoadFailed(final String errorMessage) {
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				EventMapActivity.this.showProgressDialog(false);
				Toast.makeText(EventMapActivity.this, "failed to load list", 1000).show();
			}			
		});		
	}

	
	/**
	 * load events onto map
	 */
	
	private void loadEventsOntoMap(List<ICoreViewModel> eventItems){
		// clear the events
		this.events.clear();
		// loop through creating a marker for each
		for(int index=0;index<eventItems.size();index++){
			// get the event
			EventViewModel viewModel = (EventViewModel) eventItems.get(index);
			// add a marker to the map
			Marker marker = EventMapActivity.this.addMaker(viewModel);
			// join the new marker to the event
			viewModel.setMapMarkerId(marker.getId());
			// add the event to the list
			this.events.add(viewModel);					
		}
		// center map around events in the list
		this.centerToEvents();		
	}

	/**
	 * add a marker for that event (along with its image)
	 */
	
	private Marker addMaker(EventViewModel viewModel){

		Bitmap icon = null;
		if(viewModel!=null && viewModel.getModel()!=null && viewModel.getModel().getImageData()!=null){
			byte[] bitmapdata = viewModel.getModel().getImageData();
			Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
			icon = this.imageWrapper.resizeImage(bitmap, 150);
		}
		
		Marker marker = EventMapActivity.this.addMarker(viewModel, icon);
		return marker;
	}

	private Marker addMarker(EventViewModel viewModel, Bitmap icon){

		LatLng locationPoint = new LatLng(viewModel.getModel().getLatitude(), viewModel.getModel().getLongitude());
		MarkerOptions markerOptions = new MarkerOptions();
		markerOptions.position(locationPoint);	
		markerOptions.title(viewModel.getModel().getTitle());
		markerOptions.snippet(viewModel.getModel().getTags());	
		
		if(icon==null){
			float markerColor = BitmapDescriptorFactory.HUE_AZURE;
			markerOptions.icon(BitmapDescriptorFactory.defaultMarker(markerColor));			
		}else{
			markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
		}
		
		Marker marker = this.googleMap.addMarker(markerOptions);
		return marker;				
	}	

	private BitmapDescriptor buildDescriptor(Bitmap image){
		BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(image);
		return bitmapDescriptor;
	}


	/*
	private void addPhotoToMarker(Marker marker, Bitmap bm){
		BitmapDescriptor bitmapDescriptor = this.buildDescriptor(bm);
		LatLng firstPosition = marker.getPosition();
		LatLng secondPosition = new LatLng( firstPosition.latitude + 1, firstPosition.longitude + 1);
		LatLngBounds bounds = new LatLngBounds(firstPosition, secondPosition);
		this.addGroundOverlay(bitmapDescriptor, bounds, (float) 0.5);		
	}
	 */

	/*
	private GroundOverlay addGroundOverlay(BitmapDescriptor image, LatLngBounds bounds, float transparency){

		 GroundOverlay groundOverlay = this.googleMap.addGroundOverlay(new GroundOverlayOptions()
		      .image(image)
		      .positionFromBounds(bounds)
		      .transparency(transparency));

		 return groundOverlay;
	}
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item){

		switch(item.getItemId()){
		case R.id.menu_add_event:
			Intent addEventIntent = new Intent(this, UploadEventActivity.class);
			this.startActivity(addEventIntent);
			break;
		case R.id.menu_list_events:
			Intent listEventsIntent = new Intent(this, EventListActivity.class);
			this.startActivity(listEventsIntent);
			break;
		case R.id.menu_center_on_events:
			this.centerToEvents();
			break;
		case R.id.menu_refresh_events:
			this.googleMap.clear();
			this.requestEvents(true);
			break;
		case R.id.menu_hybrid:
			this.googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			break;
		case R.id.menu_normal:
			this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			break;
		case R.id.menu_satelite:
			this.googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			break;				
		}

		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onMapLongClick(LatLng point) {
	}
	
	@Override
	public void onInfoWindowClick(Marker marker) {	
		for(ICoreViewModel model:this.events){
			EventViewModel eventViewModel = (EventViewModel) model;
			if(eventViewModel.getMapMarkerId()!=null){
				if(eventViewModel.getMapMarkerId().equals(marker.getId())){
					Intent showEvent = new Intent(this, EventViewActivity.class);
					showEvent.putExtra(EventViewActivity.EVENT_VIEW_MODEL_INTENT_VALUE, eventViewModel);
					this.startActivity(showEvent);
				}
			}			
		}
	}	

	@Override
	public boolean onMarkerClick(final Marker marker) {	
		marker.showInfoWindow();		
		return false;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.android.gms.maps.LocationSource#activate(com.google.android.gms.maps.LocationSource.OnLocationChangedListener)
	 */


	@Override
	public void activate(OnLocationChangedListener listener) 
	{
		locationListener = listener;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.google.android.gms.maps.LocationSource#deactivate()
	 */

	@Override
	public void deactivate() 
	{
		locationListener = null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.location.LocationListener#onLocationChanged(android.location.Location)
	 */

	@Override
	public void onLocationChanged(Location location) 
	{		
		if( locationListener != null )
		{
			locationListener.onLocationChanged( location );
			// this.googleMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));
			this.centerToLocation();
		}
	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		Toast.makeText(this, "provider disabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		Toast.makeText(this, "provider enabled", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// Toast.makeText(this, "status changed", Toast.LENGTH_SHORT).show();
	}
	
	/*
	 * Progress Dialog (because we're inheriting from FragmentActivity not CoreActivity
	 */
	
	protected void showProgressDialog(boolean show){
		this.showProgressDialog(show,"loading events...");
	}	
	protected void showProgressDialog(boolean show, int resourceId){
		String progressMessage = this.getResources().getString(resourceId);
		this.showProgressDialog(show,progressMessage);
	}
	protected void showProgressDialog(final boolean show, final String progressMessage){
		runOnUiThread(new Runnable() {
			public void run() {
				if(EventMapActivity.this.progressDialog!=null){
					EventMapActivity.this.progressDialog.dismiss();
				}
				if(show){
					EventMapActivity.this.progressDialog = ProgressDialog.show(EventMapActivity.this, null, progressMessage, true, false);
				}
			}
		});
	}	

}
