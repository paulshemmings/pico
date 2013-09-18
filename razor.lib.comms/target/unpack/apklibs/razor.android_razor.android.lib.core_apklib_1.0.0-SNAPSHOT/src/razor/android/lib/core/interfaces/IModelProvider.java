package razor.android.lib.core.interfaces;

import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;


public interface IModelProvider {
	
	IModelItem getItem(Integer id);
	Map<Integer,IModelItem> getItems();
	List<IModelItem> getItemsList();
	int InsertOrUpdate(IModelItem item);
	void Delete(IModelItem item);
	void Delete(Integer id);
	
	void onCreate(SQLiteDatabase db);
	void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
}
