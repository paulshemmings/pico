package razor.android.lib.core.store;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import razor.android.lib.core.helpers.LogHelper;
import razor.android.lib.core.interfaces.ICacheable;

public class CacheHolder {

	private static Map<String,ICacheable> items = new HashMap<String,ICacheable>();
	private static final long TIME_OUT_IN_MILLISECONDS = 3600000; // 60 minutes
	private static final String TAG = "CacheHolder";

	/**
	 * Add a new cacheable item to the holder 
	 * @param cache	: ICachable
	 * @return
	 */
	
	public synchronized static boolean addItem(ICacheable cache){
		return addItem(cache.getName(),cache);
	}
	
	/**
	 * Add a new cacheable item to the holder 
	 * @param key	: String
	 * @param cache	: ICachable
	 * @return
	 */
	
	public synchronized static boolean addItem(String key, ICacheable cache){
		if(items!=null && !items.containsKey(key)){
			LogHelper.i(TAG, "%s: Add Cache Item",key);
			items.put(key,cache);
			return true;
		}
		return false;
	}
	
	/**
	 * Retrieve a cached item using its key 
	 * @param key : String
	 * @return	  : ICachable
	 */
	
	public synchronized static ICacheable getItem(String key){
		ICacheable cachedItem = null;
		try{
		if(items!=null && items.containsKey(key)){
			LogHelper.i(TAG, "%s: Get Cache Item",key);
			cachedItem = items.get(key);
			if(cachedItem!=null && cachedItem.getDateCached()!=null){
				java.util.Date currentDate = new Date();			
				long difference = currentDate.getTime() - cachedItem.getDateCached().getTime();
				if(difference > 0 && difference < TIME_OUT_IN_MILLISECONDS ){
					LogHelper.i(TAG, "%s: return cached item",key);
				}else{
					LogHelper.i(TAG, "%s: item timed out after %d milliseconds",key,difference);
					removeItem(key);
					cachedItem = null;
				}
			}
		}
		}catch(Exception ex){
			LogHelper.e(TAG, "getItem failed with %s",ex.getMessage());
		}
		return cachedItem;
	}
	
	/**
	 * Remove an item
	 * @param key : String
	 */
		
	public synchronized static void removeItem(String key){
		if(items!=null && items.containsKey(key)){
			LogHelper.i(TAG, "%s: Remove Cache Item",key);
			items.remove(key);
		}
	}
	
	/**
	 * Swap out the value assigned to a key
	 * @param key	: String
	 * @param cache	: ICachable
	 * @return
	 */
	
	public synchronized static boolean swapItem(String key, ICacheable cache){
		LogHelper.i(TAG, "%s: Swap out cache value",key);
		removeItem(key);
		return addItem(key,cache);
	}
	
	/**
	 * Clear all items from cache
	 */
	
	public synchronized static void clear(){
		LogHelper.i(TAG, "Clear all cache items");
		items.clear();
	}
}
