package razor.android.pico.holders;

import java.util.List;

import razor.android.pico.R;
import razor.android.pico.adapters.EventListAdapter;
import razor.android.pico.viewmodels.EventViewModel;
import razor.android.lib.core.adapters.CoreViewModelListAdapter;
import razor.android.lib.core.adapters.CoreViewModelListAdapter.OnCoreViewModelListAdapterListener;
import razor.android.lib.core.helpers.EndlessScrollListener.OnLoadMoreDataListener;
import razor.android.lib.core.holders.ViewModelListViewHolder;
import razor.android.lib.core.holders.ViewModelListViewHolder.ViewModelListViewAdapterBuilder;
import razor.android.lib.core.interfaces.ICoreViewModel;
import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

/**
 * 
 * @author phemmings
 */

public class EventListViewHolder implements OnLoadMoreDataListener, 
											ViewModelListViewAdapterBuilder,
											OnClickListener, 
											OnLongClickListener, 
											OnCoreViewModelListAdapterListener, 
											OnEditorActionListener {
	
	public static final int LayoutId = R.layout.event_list_layout;
	
	public interface OnEventListViewEventListener{
		void onMoreEventsRequested(int page);
		void onNearbyEventsRequested();
		void onNoEventsFound();
		void onEventListItemSelected(EventViewModel viewModel);
		void onEventMapRequested(List<ICoreViewModel> items);
	}

	private ViewModelListViewHolder viewModelListHolder = null;

	private int pageNumber = 1;
	private List<ICoreViewModel> currentList = null;
	private OnEventListViewEventListener listener = null;
	
	public void setPageNumber(Integer pageNumber){
		this.pageNumber = pageNumber;
	}
	
	public EventListViewHolder(OnEventListViewEventListener listener, View parentView){
		this.listener = listener;
        
        this.viewModelListHolder = new ViewModelListViewHolder(parentView);
        this.viewModelListHolder.setOnMoreDataListener(this);
        this.viewModelListHolder.setBuilder(this);
        //this.viewModelListHolder.setNoResultMessage("no results");
	}
	
	public void showLoading(boolean show){
		this.viewModelListHolder.showLoading(show);
	}
	
	public void updateEvents(List<ICoreViewModel> events){
		if(events!=null && events.size()>0){
			this.viewModelListHolder.showNoResults(false);
			this.currentList = events;
			if(this.pageNumber==1){
				this.viewModelListHolder.clearAdapter();
				this.viewModelListHolder.setAdapter(events, this);	
			}else{
				this.viewModelListHolder.updateAdapter(events);
			}			
		} else{
			if(this.pageNumber==1){
				this.viewModelListHolder.clearAdapter();
			}
			this.listener.onNoEventsFound();
		}
		
		if (this.viewModelListHolder.getSize() == 0){
			this.viewModelListHolder.showNoResults(true);
			this.listener.onNoEventsFound();			
		} 		
	}	
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */

	@Override
	public boolean onLongClick(View v) {
		this.listener.onEventMapRequested(this.currentList);	
		return this.currentList!=null;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */

	@Override
	public void onClick(View v) {
		if(this.listener!=null){
			switch(v.getId()){
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bbconnect.recipient.holders.ViewModelListViewHolder.ViewModelListViewAdapterBuilder#createAdapter(android.content.Context, java.util.List)
	 */

	@Override
	public CoreViewModelListAdapter createAdapter(Context context,List<ICoreViewModel> models) {
		return new EventListAdapter(context,models);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bbconnect.lib.helpers.EndlessScrollListener.OnLoadMoreDataListener#onScrollRequestsMoreData(int)
	 */

	@Override
	public void onScrollRequestsMoreData(int page) {
		this.pageNumber = page;
		this.listener.onMoreEventsRequested(page);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.bbconnect.lib.adapters.CoreViewModelListAdapter.OnCoreViewModelListAdapterListener#onListItemSelected(android.view.View)
	 */
	
	@Override
	public void onListItemSelected(View view) {
		EventViewModel institution = (EventViewModel) view.getTag();
		this.listener.onEventListItemSelected(institution);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.widget.TextView.OnEditorActionListener#onEditorAction(android.widget.TextView, int, android.view.KeyEvent)
	 */

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
	        this.pageNumber = 1;
	        this.listener.onMoreEventsRequested(this.pageNumber);  	        
            return true;
        }
        return false;
	}		

	private static void hideKeyboard(View view) {
	    Context context = view.getContext();
	    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
