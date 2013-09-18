package razor.android.lib.core;

import razor.android.lib.core.R;
import razor.android.lib.core.helpers.EndlessScrollListener.OnLoadMoreDataListener;
import razor.android.lib.core.holders.ViewModelListViewHolder;
import android.os.Bundle;

public abstract class CoreViewModelListActivity extends CoreActivity implements OnLoadMoreDataListener {

	protected ViewModelListViewHolder viewModelListHolder = null;
	protected int pageNumber = 1;
	protected final int pageSize = 10;
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.setContentView(R.layout.lib_viewmodel_list_layout);        
        this.viewModelListHolder = new ViewModelListViewHolder(this.findViewById(R.id.viewmodel_list));
        this.viewModelListHolder.setOnMoreDataListener(this);
    }
    
    protected ViewModelListViewHolder getViewModelListViewHolder (){
    	return this.viewModelListHolder;
    }
    
    protected void setViewModelListViewHolder (ViewModelListViewHolder viewHolder){
    	this.viewModelListHolder = viewHolder;
    }
        
}
