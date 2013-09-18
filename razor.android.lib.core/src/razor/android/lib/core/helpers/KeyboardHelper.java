package razor.android.lib.core.helpers;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardHelper {
	/**
	 * Shows the keyboard.
	 * @param view
	 */
	
	public static void showKeyboard(View view) {
	    Context context = view.getContext();
	    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	/**
	 * Hides the keyboard.
	 * @param view
	 */
	
	public static void hideKeyboard(View view) {
	    Context context = view.getContext();
	    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
	    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
