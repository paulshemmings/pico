package razor.android.pico.activities;

import java.util.List;

import razor.android.pico.adapters.EventListAdapter;
import razor.android.pico.handlers.EventHandler;
import razor.android.pico.handlers.EventHandler.onEventsListedListener;
import razor.android.pico.viewmodels.EventViewModel;
import razor.android.lib.core.adapters.CoreViewModelListAdapter;
import razor.android.lib.core.adapters.CoreViewModelListAdapter.OnCoreViewModelListAdapterListener;
import razor.android.lib.core.CoreViewModelListActivity;
import razor.android.lib.core.holders.ViewModelListViewHolder.ViewModelListViewAdapterBuilder;
import razor.android.lib.core.interfaces.ICoreViewModel;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class EventListActivity 	extends 	CoreViewModelListActivity
								implements 	onEventsListedListener, 
											OnCoreViewModelListAdapterListener {

	/**
	 * Create
	 */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        this.viewModelListHolder.setBuilder( new ViewModelListViewAdapterBuilder(){
			public CoreViewModelListAdapter createAdapter(Context context,List<ICoreViewModel> models) {
				return new EventListAdapter(context,models);
			}        	
        });
        
        this.pageNumber = 1;
        this.requestEventList();
    }
    
    /**
     * Resume
     */

    public void onResume(){
        super.onResume();
    }    
    
    /**
     * Not paginated
     */
    
	@Override
	public void onScrollRequestsMoreData(int page) {
		// this.pageNumber = page;
		// this.requestEventList();
	}    
	
	/**
	 * Get events
	 */
    
	private void requestEventList(){
		this.showProgressDialog(true);
		EventHandler handler = new EventHandler(this);
		handler.listEvents(this, true, null);
	}    
	
	/**
	 * Handle events
	 */
    
	@Override
	public void onEventItemsLoaded(final List<ICoreViewModel> eventItems) {
		this.runOnUiThread(new Runnable(){
			public void run() {
				EventListActivity.this.showProgressDialog(false);
				EventListActivity.this.pageNumber = 1;
				EventListActivity.this.updateEventItems(eventItems);
			}			
		});
	}    
	
	/**
	 * update the event items
	 */
	
	private void updateEventItems(List<ICoreViewModel> items){
		if(items!=null){
			if(this.pageNumber==1){
				this.viewModelListHolder.clearAdapter();
				this.viewModelListHolder.setAdapter(items, this);	
			}else{
				this.viewModelListHolder.updateAdapter(items);
			}			
		} else {
			Toast.makeText(EventListActivity.this,"No items found",1000).show();
		}
	}
	
	/**
	 * Event item selected
	 */

	@Override
	public void onListItemSelected(View v) {		
		ICoreViewModel viewModel = (ICoreViewModel) v.getTag();
		EventViewModel eventViewModel = (EventViewModel) viewModel;
		Intent showEvent = new Intent(this, EventViewActivity.class);
		showEvent.putExtra(EventViewActivity.EVENT_VIEW_MODEL_INTENT_VALUE, eventViewModel);
		this.startActivity(showEvent);
	}
	
	/**
	 * Events failed to load
	 */

	@Override
	public void onEventItemsLoadFailed(final String errorMessage) {
		this.runOnUiThread(new Runnable(){
			@Override
			public void run() {
				EventListActivity.this.showProgressDialog(false);
				Toast.makeText(EventListActivity.this, errorMessage, 1000).show();
			}			
		});		
	}
	

}