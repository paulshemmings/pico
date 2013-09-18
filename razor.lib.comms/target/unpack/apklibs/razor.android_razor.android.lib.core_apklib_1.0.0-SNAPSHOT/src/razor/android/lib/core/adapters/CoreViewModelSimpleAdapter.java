package razor.android.lib.core.adapters;

import java.util.List;

import razor.android.lib.core.interfaces.ICoreViewModel;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CoreViewModelSimpleAdapter extends ArrayAdapter<ICoreViewModel> implements View.OnClickListener {

	public interface OnCoreViewModelSimpleAdapterListener{
		public void onSimpleListItemSelected(View view);
	}
	
	private OnCoreViewModelSimpleAdapterListener listener;
	
	public CoreViewModelSimpleAdapter(Context context, List<ICoreViewModel> objects) {
		super(context, android.R.layout.simple_dropdown_item_1line, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;
 
		if (null == convertView) {
			LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = li.inflate(android.R.layout.simple_dropdown_item_1line, null);
		} else {
			row = convertView;
		}
 
		TextView tv = (TextView) row.findViewById(android.R.id.text1);
		tv.setText(getItem(position).getTitle());
 
		row.setTag(getItem(position).getID());
		if(this.listener!=null) row.setOnClickListener(this);
		
		return row;
	}

	public void setListener(OnCoreViewModelSimpleAdapterListener listener) {this.listener = listener;}
	public OnCoreViewModelSimpleAdapterListener getListener() {return listener;}


	public void onClick(View v) {
		if(CoreViewModelSimpleAdapter.this.listener!=null){
			CoreViewModelSimpleAdapter.this.listener.onSimpleListItemSelected(v);
		}		
	}	

}
