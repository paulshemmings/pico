package razor.android.lib.core.helpers;

import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class EndlessScrollListener implements OnScrollListener {
	
	public interface OnLoadMoreDataListener{
		public void onScrollRequestsMoreData(int page);
	}
	
    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private OnLoadMoreDataListener onLoadDataListener;
 
    public EndlessScrollListener() {
    }
    public EndlessScrollListener(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }
    
    public void setMoreDataListener(OnLoadMoreDataListener listener){
    	this.onLoadDataListener = listener;
    }
    
    public int getCurrentPage(){return this.currentPage;}
    public void setCurrentPage(int currentPage){this.currentPage = currentPage;}
    public void reset(){
    	this.currentPage = 0;
    	this.previousTotal = 0;
    	this.loading = true;    	
    }
 
    public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
        	if(this.onLoadDataListener!=null){
        		this.onLoadDataListener.onScrollRequestsMoreData(currentPage+1);
        	}
            loading = true;
        }
    }
 
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }
}
