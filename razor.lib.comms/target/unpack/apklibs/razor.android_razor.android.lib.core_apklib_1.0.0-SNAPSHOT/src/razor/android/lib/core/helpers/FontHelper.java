package razor.android.lib.core.helpers;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Typeface;

public class FontHelper {

	private Map<String,Typeface> fonts = new HashMap<String,Typeface>();
	
	private static final String Font_Value_Georgia_Bold_Italic = "font_georgia_bold_italic";
	private static final String Font_Value_Georgia_Bold = "font_georgia_bold";
	private static final String Font_Value_Georgia_Italic = "font_georgia_italic";
	private static final String Font_Value_Georgia = "font_georgia";
	private static final String Font_Value_Lucida_Grande = "font_lucida_grande";
	private static final String Font_Value_Verdana = "font_verdana";
	 	   
	public FontHelper(Context parentContext){
		this.loadFonts(parentContext);		
	}
	
	public Typeface getFont(String font) {
		return fonts.get(font);
	}
	
	private void loadFonts(Context parentContext) {
		
		/*
		 * can't find an easy way to load the assets from an input stream
		 
		Configuration c = new Configuration(); 
		c.setToDefaults(); 
		InputStream myInput = new Resources(parentContext.getAssets(), null, c).openRawResource(R.raw.georgia);
		*/
		
		fonts.put(Font_Value_Georgia_Bold_Italic, Typeface.createFromAsset(parentContext.getAssets(), "fonts/georgia_bold_italic.ttf"));
		fonts.put(Font_Value_Georgia_Bold, Typeface.createFromAsset(parentContext.getAssets(), "fonts/georgia_bold.ttf"));
		fonts.put(Font_Value_Georgia_Italic, Typeface.createFromAsset(parentContext.getAssets(), "fonts/georgia_italic.ttf"));
		fonts.put(Font_Value_Georgia, Typeface.createFromAsset(parentContext.getAssets(), "fonts/georgia.ttf"));
		fonts.put(Font_Value_Lucida_Grande, Typeface.createFromAsset(parentContext.getAssets(), "fonts/lucida_grande.ttf"));
		fonts.put(Font_Value_Verdana, Typeface.createFromAsset(parentContext.getAssets(), "fonts/verdana.ttf"));
	}
		 
}
