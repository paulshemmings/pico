package razor.android.lib.core.holders;

import android.view.View;
import android.view.View.OnClickListener;

public class TwoButtonTemplateViewHolder implements OnClickListener {

	public interface OnTwoButtonTemplateEventListener{
		void onTwoButtonCommit();
		void onTwoButtonCancel();
	}
	
	private OnTwoButtonTemplateEventListener eventListener;
	
	public TwoButtonTemplateViewHolder(OnTwoButtonTemplateEventListener eventListener,View parentView){
		this.eventListener = eventListener;
		parentView.findViewById(razor.android.lib.core.R.id.lib_two_button_template_action_commit).setOnClickListener(this);
		parentView.findViewById(razor.android.lib.core.R.id.lib_two_button_template_action_cancel).setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		if(this.eventListener!=null){
			if(v.getId()==razor.android.lib.core.R.id.lib_two_button_template_action_commit){
				this.eventListener.onTwoButtonCommit();
			}
			if(v.getId()==razor.android.lib.core.R.id.lib_two_button_template_action_cancel){
				this.eventListener.onTwoButtonCancel();
			}
		}
	}
}
