package razor.android.lib.core.handlers;

import android.content.Context;

public abstract class AbstractHandler {
	private Context parentContext;

	public AbstractHandler(Context parentContext){
		this.parentContext = parentContext;		
	}
	
	public void setParentContext(Context parentContext) {this.parentContext = parentContext;}
	public Context getParentContext() {return parentContext;} 
}
