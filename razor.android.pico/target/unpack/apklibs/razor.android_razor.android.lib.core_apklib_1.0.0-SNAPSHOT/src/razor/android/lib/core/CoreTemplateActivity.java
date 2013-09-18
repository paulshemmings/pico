package razor.android.lib.core;

import razor.android.lib.core.R;
import razor.android.lib.core.helpers.LayoutHelper;
import razor.android.lib.core.interfaces.ICoreTemplateActivity;
import android.os.Bundle;
import android.view.View;

public class CoreTemplateActivity extends CoreActivity implements ICoreTemplateActivity {

	private View activityContent = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {    	
        super.onCreate(savedInstanceState);        
    }	
	
	public void setTemplateContentView(int resourceID){
		this.activityContent = LayoutHelper.setTemplateContentView(this, R.layout.lib_blank_template, R.id.lib_blank_template_content, resourceID);
	}
	
	public void setTemplateContentView(int templateId, int containerId, int resourceID){
		this.activityContent = LayoutHelper.setTemplateContentView(this, templateId, containerId, resourceID);
	}	

	public View getTemplateContentView() {
		return this.activityContent;
	}
	
    public void setBottomStubView(int layoutID){
    	LayoutHelper.setStubView(this, R.id.lib_template_bottom_stub, layoutID);
    }
    
    public void setTopStubView(int layoutID){
    	LayoutHelper.setStubView(this, R.id.lib_template_top_stub, layoutID);
    } 	 
}
