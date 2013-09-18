package razor.android.lib.core;

import java.io.Serializable;

import razor.android.lib.core.interfaces.ICoreActivity;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.view.Window;

public class CoreActivity extends Activity implements ICoreActivity {
	
	protected ProgressDialog progressDialog = null;
	
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    
    
	@Override 
	public void onDestroy()
	{
		super.onDestroy();
	}
	
	public Context getContext(){
		Context c = this;
		return c;
	}	
	
	public String getIntentValue(String name){
		return getIntent().getStringExtra(name);
	}
	
	public Serializable getIntentSerializableValue(String name){
		return getIntent().getSerializableExtra(name);
	}
	
	public void setIntentValue(Intent i, String name, String value){
		i.putExtra(name, value);
	}
	
	public void setIntentValue(Intent i, String name, Serializable value){
		i.putExtra(name, value);
	}
	
	protected void showProgressDialog(boolean show){
		this.showProgressDialog(show,"loading...");
	}	
	protected void showProgressDialog(boolean show, int resourceId){
		String progressMessage = this.getResources().getString(resourceId);
		this.showProgressDialog(show,progressMessage);
	}
	protected void showProgressDialog(final boolean show, final String progressMessage){
		runOnUiThread(new Runnable() {
			public void run() {
				if(CoreActivity.this.progressDialog!=null){
					CoreActivity.this.progressDialog.dismiss();
				}
				if(show){
					CoreActivity.this.progressDialog = ProgressDialog.show(CoreActivity.this, null, progressMessage, true, false);
				}
			}
		});
	}		
}