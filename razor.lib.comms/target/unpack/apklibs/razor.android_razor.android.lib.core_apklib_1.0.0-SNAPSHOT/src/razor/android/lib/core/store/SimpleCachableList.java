package razor.android.lib.core.store;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import razor.android.lib.core.helpers.LogHelper;
import razor.android.lib.core.interfaces.ICacheableList;

public class SimpleCachableList<T> implements ICacheableList<T> {
	protected List<T> cachedItems;
	
	private String name;
	private java.util.Date cachedDate = null;
	
	public SimpleCachableList(){
		this.cachedItems = new ArrayList<T>();
		this.name = "undefined";
		this.cachedDate = new Date();
	}
		
	public SimpleCachableList(String name){
		this.cachedItems = new ArrayList<T>();
		this.name = name;
		this.cachedDate = new Date();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void addItem(T item){
		if(this.cachedItems!=null){
			LogHelper.i(this.name, "Add cached Item");
			this.cachedItems.add(item);
		}
	}
	
	public void addItems(List<T> items){
		if(this.cachedItems!=null && items!=null){
			LogHelper.i(this.name, "Add %d Cached Items",items.size());
			for(T item:items){
				this.cachedItems.add( item);
			}
		}
	}	
	
	public void clear(){
		LogHelper.i(this.name, "Clear all cached items");
		this.cachedItems.clear();
	}
	
	public T getItem(){
		if(this.cachedItems!=null&&this.cachedItems.size()>0){
			LogHelper.i(this.name, "Get %d Cache Items",this.cachedItems.size());
			return this.cachedItems.get(0);
		}
		return null;
	}
	
	@Override
	public List<T> getItemList() {
		return this.cachedItems;
	}

	@Override
	public Date getDateCached() {
		return this.cachedDate;
	}
}

