package razor.android.lib.core.helpers;

import razor.android.lib.core.helpers.LogHelper;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ApplicationPreferences {
     private static final String APP_SHARED_PREFS = "razor.event.tracker.helpers.preferences"; 
     private static final String LOG_TAG = "ApplicationPreferences";
     private SharedPreferences appSharedPrefs;
     private Editor prefsEditor;
              
     private static String getApplicationPreferenceName(){
    	 return APP_SHARED_PREFS;
     }
     
     public ApplicationPreferences(Context context)
     {
    	 String preferenceKey = getApplicationPreferenceName();
    	 LogHelper.i(LOG_TAG,"create preferences with key %s",preferenceKey);
         this.appSharedPrefs = context.getSharedPreferences(preferenceKey, Activity.MODE_PRIVATE);
         this.prefsEditor = appSharedPrefs.edit();
     }
     
     public String getPreference(String key, String defaultValue) {
    	 LogHelper.i(LOG_TAG,"get preferences %s",key);
         return appSharedPrefs.getString(key, defaultValue);
     }

     public void setPreference(String key, String value) {
    	 LogHelper.i(LOG_TAG,"set preferences %s with value %s",key,value);
         prefsEditor.putString(key, value);
         prefsEditor.commit();
     }
     
}
