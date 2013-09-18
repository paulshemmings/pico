package razor.android.lib.core.helpers;

import razor.android.lib.core.CoreApplication;


public class ApplicationPreferenceHolder {
	
	private static ApplicationPreferences preferences = new ApplicationPreferences(CoreApplication.getCoreApplicationContext());
	
	public static ApplicationPreferences getGeneralPreferences(){
		return preferences;
	}
	
}
