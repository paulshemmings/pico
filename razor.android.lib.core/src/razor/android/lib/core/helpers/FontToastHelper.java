package razor.android.lib.core.helpers;

import razor.android.lib.core.R;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FontToastHelper {

	// http://developer.android.com/guide/topics/ui/notifiers/toasts.html#CustomToastView
	// http://stackoverflow.com/questions/3619670/how-can-i-find-the-layout-parameters-of-the-toast-widget
	
	public static class CallbackToast{

		private Toast innerToast = null;
		
		public interface CallbackToastListener{
			void onToastClosed();
		}
		
		public CallbackToast(Context context) {
			this.innerToast = new Toast(context);
		}
		
		public void show(){
			TextView textWidget = (TextView) this.innerToast.getView().findViewById(R.id.lib_font_toast_message);
			if (!textWidget.getText().equals("NotAuthenticated")){
				this.innerToast.show();
			}
		}
		
		public void setGravity(int gravity, int xOffset, int yOffset){
			this.innerToast.setGravity(gravity, xOffset, yOffset);
		}
		
		public void setDuration(int duration){
			this.innerToast.setDuration(duration);
		}
		
		public void setView(View view){
			this.innerToast.setView(view);
		}
		
		public void show(final CallbackToastListener listener){
			final int duration = this.innerToast.getDuration();			
			
			Thread t = new Thread(new Runnable(){
				public void run() {
					try {
						Thread.sleep(duration);
					} catch (InterruptedException e) {
						e.printStackTrace();
						LogHelper.e("CallbackToast", String.format("%s:%s","Failed to wait duration of toast",e.getMessage()));
						listener.onToastClosed();
					}
					listener.onToastClosed();
				}
			});
			
			this.innerToast.show();
			t.start();
		}
		
	}
	
	public static CallbackToast makeToast(Context parentContext, int resourceId, int duration){
		String text = parentContext.getResources().getString(resourceId);
		return makeToast(parentContext,text,duration);		
	}
	
	public static CallbackToast makeToast(Context parentContext, String text, int duration){
		
		LayoutInflater inflater = (LayoutInflater)parentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.lib_font_toast_layout,null);
		
		/*
		ImageView image = (ImageView) layout.findViewById(R.id.image);
		image.setImageResource(R.drawable.android);
		*/
		TextView textWidget = (TextView) layout.findViewById(R.id.lib_font_toast_message);
		textWidget.setText(text);

		CallbackToast toast = new CallbackToast(parentContext);
		toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		toast.setDuration(duration);
		toast.setView(layout);	
		
		return toast;
	}
}
