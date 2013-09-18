package razor.android.lib.core.adapters;

import razor.android.lib.core.R;
import razor.android.lib.core.CoreExpandableActivity;
import razor.android.lib.core.interfaces.ICoreViewModel;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class CoreViewModelExpandableListAdapter extends BaseExpandableListAdapter implements View.OnClickListener {

	public interface OnCoreViewModelListAdapterListener{
		public void onChildItemSelected(View view);
	}
	
	protected CoreExpandableActivity Activity = null;
	protected ICoreViewModel[] GroupItems = null;
	public ICoreViewModel SelectedGroup = null;
	public OnCoreViewModelListAdapterListener listener = null;
	public View.OnClickListener NewRowClickListener = null;
	
	protected abstract  ICoreViewModel[] getChildItems(int groupPosition);
	protected abstract void PerformChildRowClick(View v);
	
	public abstract View getChildView(int groupPosition, int childPosition, boolean isLastChild,View convertView, ViewGroup parent);
	public abstract View getGroupView(int groupPosition, boolean isExpanded, View convertView,ViewGroup parent);
	
	public CoreViewModelExpandableListAdapter(CoreExpandableActivity activity,ICoreViewModel[] groupItems)
	{
		this.Activity = activity;
		this.GroupItems = groupItems;
	}	
	
	public void RefreshGroupItems(ICoreViewModel[] groupItems){
		this.GroupItems = groupItems;
	}
	
	public void onClick(View v) 
	{
		if(this.listener!=null){
			this.listener.onChildItemSelected(v);
		}
	}	
	
	protected View PopulateGroupView(View view, String mainText, String subText){
		if(view == null){		
			LayoutInflater li = this.Activity.getLayoutInflater();
			view = li.inflate(R.layout.lib_expandable_template_model_item_group, null);
		}
		((TextView)view.findViewById(R.id.model_item_group_header_main_text)).setText(mainText);
		((TextView)view.findViewById(R.id.model_item_group_header_sub_text)).setText(subText);
		return view;
	}
	
	protected View PopulateChildView(View view, String mainText, String subText, int imageResourceID){
		if( view == null ){
			LayoutInflater li = this.Activity.getLayoutInflater();
			view = li.inflate(R.layout.lib_expandable_template_model_item_child, null);			
	        view.setOnClickListener(this);						
		}
		((TextView)view.findViewById(R.id.model_item_child_main_text)).setText(mainText);
		((TextView)view.findViewById(R.id.model_item_child_sub_text)).setText(subText);
		
		if(imageResourceID==-1){
			((ImageView)view.findViewById(R.id.model_item_child_image)).setVisibility(View.INVISIBLE);
		}else{
			((ImageView)view.findViewById(R.id.model_item_child_image)).setVisibility(View.INVISIBLE);
			((ImageView)view.findViewById(R.id.model_item_child_image)).setImageResource(imageResourceID);
		}	
		return view;
	}	
	
	protected View SetGroupViewDrawable(View groupView, boolean isExpanded){
		Drawable groupDrawable = null;
		 if (isExpanded) {
			 groupDrawable = groupView.getResources().getDrawable(R.drawable.lib_orange_gradient_background);
		 } else {
			 groupDrawable = groupView.getResources().getDrawable(R.drawable.lib_blue_gradient_background);
		 }		
		 groupView.setBackgroundDrawable(groupDrawable);		 
		 return groupView;
	}
		
	public ICoreViewModel getChild(int groupPosition, int childPosition) {
		ICoreViewModel[] items =  this.getChildItems(groupPosition);
		return items == null || items.length < childPosition-1 ? null : items[childPosition];
	}

	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return 0;
	}


	public int getChildrenCount(int groupPosition) {		
		ICoreViewModel[] items = this.getChildItems(groupPosition);
		return items == null ? 0 : items.length;
	}

	public ICoreViewModel getGroup(int groupPosition) {
		return this.GroupItems[groupPosition];
	}

	public int getGroupCount() {						
		ICoreViewModel[] items =  this.GroupItems;
		return items == null ? 0 : items.length;
	}

	public long getGroupId(int groupPosition) {
		return 0;
	}

	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return false;
	}

}
