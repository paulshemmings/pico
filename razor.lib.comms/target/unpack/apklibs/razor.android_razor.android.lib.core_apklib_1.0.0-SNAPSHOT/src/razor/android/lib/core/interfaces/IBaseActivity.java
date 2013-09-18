package razor.android.lib.core.interfaces;

import razor.android.lib.core.helpers.DatabaseHelper;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;

public interface IBaseActivity {
	LayoutInflater getLayoutInflater();
	Context getContext();
	DatabaseHelper getCurrentDatabase();
	String getIntentValue(String name);
	void setIntentValue(Intent i, String name, String value);
}
