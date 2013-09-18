package razor.android.lib.core.store;

import razor.android.lib.core.interfaces.ICacheableList;
import razor.android.lib.core.interfaces.ICoreViewModel;

public class CachableCoreViewModelList extends SimpleCachableList<ICoreViewModel> implements ICacheableList<ICoreViewModel> {

	public CachableCoreViewModelList(){
		super();
	}
	
	public CachableCoreViewModelList(String name){
		super(name);
	}
}

