package razor.android.lib.core.holders;

import razor.android.lib.core.R;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TextView;

public class ThreeButtonTemplateViewHolder implements OnClickListener {

	public interface OnThreeButtonTemplateEventListener{
		void onThreeButtonPositive();
		void onThreeButtonNeutral();
		void onThreeButtonNegative();		
	}
	
	private OnThreeButtonTemplateEventListener eventListener;
	private View parentView;
	private TextView positiveView;
	private TextView neutralView;
	private TextView negativeView;
	private Integer defaultDrawable = R.drawable.lib_rectangle_default_selector;
	
	public ThreeButtonTemplateViewHolder(OnThreeButtonTemplateEventListener eventListener,View parentView){
		this(eventListener, parentView, R.drawable.lib_rectangle_default_selector);
	}
	
	public ThreeButtonTemplateViewHolder(OnThreeButtonTemplateEventListener eventListener,View parentView, Integer backgroundDrawableResourceId){
		
		this.eventListener = eventListener;
		this.parentView = parentView;
		this.defaultDrawable = backgroundDrawableResourceId;
		
		this.positiveView = (TextView) parentView.findViewById(razor.android.lib.core.R.id.lib_three_button_template_action_positive);
		this.neutralView = (TextView) parentView.findViewById(razor.android.lib.core.R.id.lib_three_button_template_action_neutral);
		this.negativeView = (TextView) parentView.findViewById(razor.android.lib.core.R.id.lib_three_button_template_action_negative);
				
		this.positiveView.setOnClickListener(this);
		this.setBackgroundDrawable(this.positiveView, this.defaultDrawable);
		
		this.neutralView.setOnClickListener(this);
		this.setBackgroundDrawable(this.neutralView, this.defaultDrawable);
		
		this.negativeView.setOnClickListener(this);
		this.setBackgroundDrawable(this.negativeView, this.defaultDrawable);
	}
	
	public void isTwoButton(boolean isTwoButton){
		if(isTwoButton){
			this.neutralView.setVisibility(View.GONE);
			this.positiveView.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,05.f));
			this.negativeView.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,05.f));
		}else{
			this.neutralView.setVisibility(View.VISIBLE);
			this.positiveView.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,03.f));
			this.neutralView.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,03.f));
			this.negativeView.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,03.f));
		}
	}

	public void setPositive(String text){ this.positiveView.setText(text);} 
	public void setNeutral(String text){ this.neutralView.setText(text);}
	public void setNegative(String text){ this.negativeView.setText(text);}

	public void setPositive(Integer resourceId){ this.positiveView.setText(this.parentView.getResources().getString(resourceId));} 
	public void setNeutral(Integer resourceId){ this.neutralView.setText(this.parentView.getResources().getString(resourceId));}
	public void setNegative(Integer resourceId){ this.negativeView.setText(this.parentView.getResources().getString(resourceId));}
	
	public void setBackgroundDrawable(View view, Integer backgroundDrawableResourceId){		
		Drawable backgroundDrawable = backgroundDrawableResourceId == null ? null : this.parentView.getContext().getResources().getDrawable(backgroundDrawableResourceId);
		view.setBackgroundDrawable(backgroundDrawable);
	}		
	
	public void resetActionButtons(){
		this.positiveView.setEnabled(true);
		this.negativeView.setEnabled(true);
		this.neutralView.setEnabled(true);
	}
	
	@Override
	public void onClick(View v) {
		if(this.eventListener!=null){
			if(v.getId()==razor.android.lib.core.R.id.lib_three_button_template_action_positive){
				this.positiveView.setEnabled(false);
				this.eventListener.onThreeButtonPositive();
			}
			if(v.getId()==razor.android.lib.core.R.id.lib_three_button_template_action_neutral){
				this.neutralView.setEnabled(false);
				this.eventListener.onThreeButtonNeutral();
			}
			if(v.getId()==razor.android.lib.core.R.id.lib_three_button_template_action_negative){
				this.negativeView.setEnabled(false);
				this.eventListener.onThreeButtonNegative();
			}			
		}
	}


}
