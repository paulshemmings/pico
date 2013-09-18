package razor.android.lib.core.interfaces;

import java.util.Map;

import android.database.Cursor;

public interface IModelCursor {
	
	
	Cursor cursorAll();
	Cursor cursorById(Integer id);
	Cursor cursorByArgs(String whereClause, String[] args);
	
	Integer getIdFromCursor(Cursor c);
	Map<Integer,IModelItem> getItems(Cursor c);
	IModelItem getItemFromCursor(Cursor c);
		
}
