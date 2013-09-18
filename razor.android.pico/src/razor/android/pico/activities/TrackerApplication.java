package razor.android.pico.activities;

import java.util.UUID;

import razor.android.pico.R;
import razor.android.lib.core.CoreApplication;
import razor.android.lib.core.helpers.ApplicationPreferenceHolder;
import razor.android.lib.core.interfaces.IBetterLocationListener;
import razor.android.lib.core.wrappers.BetterLocationWrapper;
import android.content.Context;
import android.location.Location;


/**
 * The main application. Extends CoreApplication 
 * @author phemmings 
 */

public class TrackerApplication extends CoreApplication implements IBetterLocationListener {

	private Location currentBestLocation = null;
	private BetterLocationWrapper betterLocationWrapper = null;
	private String maximumMapZoom = null;
	private UUID privateUniqueIdentifiyer = null;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// monitor the current location (disable monitoring for this release)
		this.betterLocationWrapper = new BetterLocationWrapper();
		this.betterLocationWrapper.monitorLocation(this,this);
	}
	
	/**
	 * hacky way to get an instance of this
	 * @return
	 */
	
	public static TrackerApplication instance(){
		return (TrackerApplication) CoreApplication.getCoreApplication();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bbconnect.lib.interfaces.IBetterLocationListener#onLocationUpdated(android.location.Location)
	 */

	@Override
	public void onLocationUpdated(Location location) {
		this.setCurrentBestLocation(location);
	}

	public void setCurrentBestLocation(Location currentBestLocation) {
		this.currentBestLocation = currentBestLocation;
	}

	public Location getCurrentBestLocation() {
		return currentBestLocation;
	}
	
	public UUID getUniqueIdentifier(){
		if(this.privateUniqueIdentifiyer==null){
			String uuIdValue = ApplicationPreferenceHolder.getGeneralPreferences().getPreference("UUID", java.util.UUID.randomUUID().toString());
			this.privateUniqueIdentifiyer = java.util.UUID.fromString(uuIdValue);			
		}
		return this.privateUniqueIdentifiyer;
	}
	
	public float getMaximumMapZoom(){
		if(this.maximumMapZoom==null){
			Context context = this.getApplicationContext();
			this.maximumMapZoom = context.getResources().getString(R.string.maximum_map_zoom);
		}
		return Float.valueOf(this.maximumMapZoom).floatValue();
	}

}
