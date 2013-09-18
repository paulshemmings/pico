package razor.android.lib.core.adapters;

import razor.android.lib.core.interfaces.ICoreViewModel;
import razor.android.lib.core.interfaces.ICoreViewModelContainer;

public class CoreViewModelContainer implements ICoreViewModelContainer {
	
	private ICoreViewModel containedModel = null;

	@Override
	public ICoreViewModel getModel() { return this.containedModel;}

	@Override
	public void setModel(ICoreViewModel model) {this.containedModel = model;}
	
}
