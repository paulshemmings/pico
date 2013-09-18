package razor.android.lib.core.adapters;

import java.util.List;

import razor.android.lib.core.interfaces.ICoreViewModel;
import razor.android.lib.core.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CoreViewModelSpinnerArrayAdapter extends ArrayAdapter<ICoreViewModel>{
	
	protected LayoutInflater inflater = null;
	protected List<ICoreViewModel> modelList = null;
	public Object SelectedGroup = null;
	
	public CoreViewModelSpinnerArrayAdapter(Context context,List<ICoreViewModel> models) {
		super(context, R.layout.lib_spinner_row, models);
		this.modelList = models;
	}
	
	protected Integer getSpinnerItemIcon(int position)
	{
		return null;
	}
	
	public Integer getPosition(Long modelId){
		Integer position = 0;
		for(int pos=0;pos<this.modelList.size();pos++){
			if( this.modelList.get(pos).getID().equals(modelId) ){
				position = pos;
				break;
			}
		}
		return position;
	}
	
	public ICoreViewModel getObjectItem(int position){
		return this.modelList.get(position);
	}
	
	public void RefreshObjectArray(List<ICoreViewModel> models){
		this.modelList = models;
		this.clear();
		for(ICoreViewModel model:models){
			this.add(model);
		}		
		this.notifyDataSetChanged();
	}
	
	protected String getSpinnerItemText(int position) {
		return this.getObjectItem(position).getTitle();
	}	

	@Override
	public View getDropDownView(int position, View convertView,ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {

		LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View row = li.inflate(R.layout.lib_spinner_row, parent, false);
		
		TextView label=(TextView)row.findViewById(R.id.spinner_row_text);
		label.setText(this.getSpinnerItemText(position));
		
		//ImageView icon=(ImageView)row.findViewById(R.id.icon);
		//icon.setImageResource(getSpinnerItemIcon(position));
		   
		return row;
	}
}

