package razor.android.lib.core.cursors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import razor.android.lib.core.interfaces.IModelCursor;
import razor.android.lib.core.interfaces.IModelItem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class BaseCursor implements IModelCursor {

	public enum eOrderDirection { ASCENDING, DESCENDING };
	protected eOrderDirection OrderDirection = eOrderDirection.ASCENDING;
	
	protected SQLiteDatabase mDB = null;		
	protected abstract String getTableName();	
	protected abstract String getOrderByClause();
	
	public BaseCursor(SQLiteDatabase db){
		this.mDB = db;
	}
	
	protected SQLiteDatabase getDatabase(){
		return this.mDB;
	}
	
	protected String getSelectString()
	{
		return String.format("SELECT * FROM %s",this.getTableName());
	}
	
	public Integer getIdFromCursor(Cursor c){
		return c.getInt(c.getColumnIndex("_id"));
	}
	
	public Cursor cursorById(Integer id)
	{
		String[] args = { id.toString() };
		return this.cursorByArgs("WHERE _ID=?",args );
	}	
	public Cursor cursorAll()
	{
		return this.cursorByArgs("", null );
	}	
	public Cursor cursorByArgs(String whereClause, String[] args)
	{
		String sql = String.format("%s %s %s", this.getSelectString(),whereClause,this.getOrderByClause());
		//Cursor row = this.getDatabase().rawQuery(this.getSelectString() + " " + whereClause + " " + this.getOrderByClause(),args);
		Cursor row = this.getDatabase().rawQuery(sql,args);
		row.moveToFirst();
		return row;
	}		
	
	protected void setOrderByDirection(eOrderDirection orderDirection)
	{
		this.OrderDirection = orderDirection;
	}
	
	protected String getOrderDirection(){
		return this.OrderDirection == eOrderDirection.ASCENDING ? " ASC " : " DESC ";
	}
	
	public IModelItem getItem(Integer id)
	{
		Cursor c = this.cursorById(id);
		IModelItem item = null;
		if( !c.isClosed() && c.getCount()>0 )
		{
			item = this.getItemFromCursor(c);
		}
		c.close();		
		return item;
	}	

	public Map<Integer,IModelItem> getItems()
	{
		Cursor c = this.cursorAll();		
		Map<Integer,IModelItem> items = this.getItems(c);		
		c.close();
		return items;
	}
		
	public Map<Integer,IModelItem> getItems(Cursor c)
	{
		Map<Integer,IModelItem> items = new HashMap<Integer,IModelItem>();
		if( !c.isClosed() && c.getCount()>0 )
		{			
			for( c.moveToFirst(); items.size()<c.getCount(); c.moveToNext())
			{			
				IModelItem item = this.getItemFromCursor(c);
				items.put( item.getID(), item );
			}
		}
		if(!c.isClosed()){
			c.close();
		}
		return items;
	}
	
	public List<IModelItem> getItemsList()
	{
		Cursor c = this.cursorAll();		
		List<IModelItem> items = this.getItemsList(c);		
		c.close();
		return items;
	}	
	
	public List<IModelItem> getItemsList(Cursor c)
	{
		List<IModelItem> items = new ArrayList<IModelItem>();
		if( !c.isClosed() && c.getCount()>0 )
		{			
			for( c.moveToFirst(); items.size()<c.getCount(); c.moveToNext())
			{			
				IModelItem item = this.getItemFromCursor(c);
				items.add( item );
			}
		}
		if(!c.isClosed()){
			c.close();
		}
		return items;
	}	
		
	protected int FinishInsertOrUpdate(ContentValues cv, Integer id){
		int returned_id;
		if (id == null)
		{
			this.getDatabase().insert(this.getTableName(), "name", cv);
			
			Cursor c = this.getDatabase().rawQuery("SELECT max(_id) as lastid FROM " + this.getTableName(),null);
			c.moveToFirst();
			returned_id = c.getInt(c.getColumnIndex("lastid"));
			c.close();
				
		} else
		{
			this.getDatabase().update(this.getTableName(), cv, "_id = " + id.intValue(), null);
			returned_id = id.intValue();
		}
		return returned_id;		
	}
	
	public void Delete(IModelItem item)
	{
		this.Delete(item.getID());
	}	
	public void Delete(Integer id)
	{
		this.getDatabase().delete(this.getTableName(), "_id = " + id, null);
	}		
	
}
