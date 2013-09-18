package razor.android.lib.core.interfaces;

import android.content.Intent;

public interface ICoreActivity {
	String getIntentValue(String name);
	void setIntentValue(Intent i, String name, String value);
}
