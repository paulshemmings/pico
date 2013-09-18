package razor.android.lib.core.widgets;

import razor.android.lib.core.R;
import razor.android.lib.core.CoreApplication;
import razor.android.lib.core.helpers.LogHelper;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Enable us to specify the font of a text view
 * @author phemmings
 */

public class FontTextView extends TextView {
    private static final String TAG = "TextView";

    /**
     * Constructor
     * @param context 	: Context
     */
    
    public FontTextView(Context context) {
        super(context);
    }
    
    /**
     * Constructor
     * @param context	: Context
     * @param attrs		: AttributeSet
     */

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
        setHtmlText(context, attrs);
    }
    
    /**
     * Constructor
     * @param context	: Context
     * @param attrs		: AttributeSet
     * @param defStyle	: int
     */

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
        setHtmlText(context, attrs);
    }
    
    /**
     * Set the text view text, specified by a string containing HTML tags
     * @param text : Integer
     */
    
    public void setHtmlText(Context ctx, Integer resourceId){
    	String htmlText = ctx.getResources().getString(resourceId);
    	super.setText(Html.fromHtml(htmlText));
    }    
    
    /**
     * Set the text view text, specified by a string containing HTML tags
     * @param text : String
     */
    
    public void setHtmlText(String text){
    	super.setText(Html.fromHtml(text));
    }
    
    /**
     * Set the text view text, specified in the mark-up attribute "htmlText"
     * @param ctx
     * @param attrs
     */
    
    private void setHtmlText(Context ctx, AttributeSet attrs){
    	try{
	        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.FontTextView);
	        String htmlText = a.getString(R.styleable.FontTextView_htmlText);
	        
	        if(htmlText!=null){
	        	this.setHtmlText(htmlText);
	        }
    	}catch(Exception e){
    		LogHelper.e(TAG, "unable to set text from htmlText tag");
    	}        
    }
    
    /**
     * Set the text view TypeFace, specified in the mark-up attribute "connectFont"
     * @param ctx	: Context
     * @param attrs	: AttributeSet
     */

    private void setCustomFont(Context ctx, AttributeSet attrs) {

    	if (isInEditMode()) {
            return;
        }
    	
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.FontTextView);
        String customFont = a.getString(R.styleable.FontTextView_connectFont);
        
        if(customFont!=null){
        	setCustomFont(ctx, customFont);
        } else {
        	LogHelper.e(TAG,"no font defined"); // can't get widget id or name :(
        }
        
        a.recycle();
    }
    
    /**
     * Set the text view Typeface, specified by the font name 
     * @param ctx	: Context
     * @param asset	: String
     * @return
     */

    private boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
        	
        	tf = CoreApplication.getCoreApplication().getFontHelper().getFont(asset);        	
        	//tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/" + asset); 
        	
        } catch (Exception e) {
            LogHelper.e(TAG, "Could not get typeface: "+e.getMessage());
            return false;
        }
        try
        {
        	setTypeface(tf);	
        } catch (Exception e) {
        	LogHelper.e(TAG, "Could not set typeface: "+e.getMessage());
            return false;
        }
        
        return true;
    }
}
