package razor.android.lib.core.adapters;

import java.util.List;

import razor.android.lib.core.R;
import razor.android.lib.core.interfaces.ICoreViewModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CoreViewModelListAdapter extends ArrayAdapter<ICoreViewModel> implements View.OnClickListener {
	
	public interface OnCoreViewModelListAdapterListener{
		public void onListItemSelected(View view);		
	}
	public interface CoreViewModelListAdapterRenderer{
		public void renderRow(View row, ICoreViewModel item);
	}
	
	private OnCoreViewModelListAdapterListener listener;
	private CoreViewModelListAdapterRenderer renderer;
	private int mainTextId = R.id.lib_list_item_main_text;
	private int subTextId = R.id.lib_list_item_sub_text;	
	private int rowLayoutId = R.layout.lib_list_simple_item; 
	
	public CoreViewModelListAdapter(Context context, List<ICoreViewModel> objects) {
		super(context, R.layout.lib_list_layout, objects);
	}
/*
	public CoreViewModelListAdapter(Context context, ICoreViewModel[] objects) {
		super(context, R.layout.lib_list_layout, objects);
	}
*/	
	public CoreViewModelListAdapter(Context context, int rowLayoutId, List<ICoreViewModel> objects) {
		super(context, rowLayoutId, objects);
		this.rowLayoutId = rowLayoutId;
	}	
	
	public CoreViewModelListAdapter(Context context, int rowLayoutId, int mainTextId, int subTextId, List<ICoreViewModel> models) {
		super(context, rowLayoutId, models);
		this.rowLayoutId = rowLayoutId;
		this.mainTextId = mainTextId;
		this.subTextId = subTextId;
	}
	
	public int getRowLayoutId(int id){return this.rowLayoutId;}
	public void setRowLayoutId(int id){this.rowLayoutId = id;}
	
	public int getMainTextId(){return this.mainTextId;}
	public void setMainTextId(int id){this.mainTextId = id;}
	
	public void setSubTextId(int id){this.subTextId = id;}	
	public int getSubTextId(){return this.subTextId;}
	
	
	public void addRange(ICoreViewModel[] models){
		for(ICoreViewModel model:models){
			this.add(model);
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
 
		if (null == convertView) {
			LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = li.inflate(this.rowLayoutId, null);
		} else {
			row = convertView;
		}
		
		ICoreViewModel item = getItem(position);
		
		if( this.renderer == null ){
			this.renderRow(row, item);	
		}else{
			this.renderer.renderRow(row, item);
		}
						
		if(this.listener!=null) row.setOnClickListener(this);		
		return row;
	}
	
	public void renderRow(View row, ICoreViewModel item){
		if(item!=null){
			TextView tv = (TextView) row.findViewById(this.mainTextId);
			tv.setText(item.getTitle());
	
			TextView stv = (TextView) row.findViewById(this.subTextId);
			stv.setText(item.getDetails() != null && item.getDetails().length>0 ? item.getDetails()[0] : "");
	
			row.setTag(item);		
		}		
	}

	public void setListener(OnCoreViewModelListAdapterListener listener) {this.listener = listener;}
	public OnCoreViewModelListAdapterListener getListener() {return listener;}

	public void onClick(View v) {
		if(CoreViewModelListAdapter.this.listener!=null){
			CoreViewModelListAdapter.this.listener.onListItemSelected(v);
		}		
	}	

}
