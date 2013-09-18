package razor.android.lib.core.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

public class LayoutHelper {

	public static View setTemplateContentView(Activity activity,int templateID, int includeID, int contentID){
        
        // inflate the template
        LayoutInflater li = activity.getLayoutInflater();
        View templateView = li.inflate(templateID, null);
        
        // inflate the content
        View activityContent = li.inflate(contentID, null);
        
        // set the view to the template
        activity.setContentView(templateView);
        
        // add the content
        ((LinearLayout)templateView.findViewById(includeID)).addView(activityContent);
        
        return templateView; // activityContent;
	}
	
	public static void setStubView(Activity activity,int stubID, int layoutID){
        ViewStub stub = (ViewStub)activity.findViewById(stubID);
        stub.setLayoutResource(layoutID);
        stub.inflate();		
	}
	
}
