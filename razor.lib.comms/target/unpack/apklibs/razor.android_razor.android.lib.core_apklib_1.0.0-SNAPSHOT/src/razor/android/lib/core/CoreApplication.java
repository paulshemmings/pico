package razor.android.lib.core;

import razor.android.lib.core.helpers.FontHelper;
import android.app.Application;
import android.content.Context;

public class CoreApplication extends Application {
	
   private static CoreApplication instance;
   private static Context context;
   private FontHelper fontHelper;
	
    public CoreApplication() 
    {
        instance = this;
    }
    
    public void onCreate()
    {
        super.onCreate();
        context =  getApplicationContext();
    }	    

    public static Context getCoreApplicationContext() {
        return context;
    }
    
    public static CoreApplication getCoreApplication(){
    	return instance;
    }
    
    public FontHelper getFontHelper(){
    	if(this.fontHelper==null)this.fontHelper=new FontHelper(this.context);
    	return this.fontHelper;
    }
    
}
