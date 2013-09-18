package razor.android.lib.core.helpers;

import java.util.Map;

import razor.android.lib.core.interfaces.IModelItem.eModelType;
import razor.android.lib.core.interfaces.IModelProvider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DatabaseHelper extends SQLiteOpenHelper
{
	// public static final String DATABASE_NAME="razor.gb.db";
	// public static final int SCHEMA_VERSION=17;

	protected Map<eModelType,IModelProvider> providers = null;
	protected abstract IModelProvider buildProvider(eModelType type);
	
	public DatabaseHelper(Context context, String databaseName, int databaseVersion)
	{
		super(context, databaseName, null, databaseVersion);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		for(eModelType type:this.providers.keySet()){
			this.providers.get(type).onCreate(db);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		for(eModelType type:this.providers.keySet()){
			// db.this.providers.get(type).onUpgrade(db);
		}

	}
	
	public IModelProvider getProvider(eModelType type)
	{
		IModelProvider provider = null;
		if(! this.providers.containsKey(type)){
			provider = this.buildProvider(type);
			if( provider != null ){
				this.providers.put(type, provider);
			}
		}
		return provider;
	}	

}







