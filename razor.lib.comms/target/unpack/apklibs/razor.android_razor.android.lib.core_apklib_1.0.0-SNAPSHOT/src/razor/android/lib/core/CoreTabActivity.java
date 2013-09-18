package razor.android.lib.core;

import razor.android.lib.core.interfaces.ICoreActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;

public class CoreTabActivity extends TabActivity implements ICoreActivity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);        
    }	
	
	public String getIntentValue(String name){
		return getIntent().getStringExtra(name);
	}
	
	public void setIntentValue(Intent i, String name, String value){
		i.putExtra(name, value);
	}
}
