package razor.android.lib.core.holders;

import razor.android.lib.core.R;
import razor.android.lib.core.helpers.Parser;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;


public class NumberValueSetter {

	public static class ViewHolder
	{
		//public LinearLayout layout;
		public View ValueSetter;
		public TextView label;
		public TextView value;
		public TextView valueLabel;
		public View minusButton;
		public View plusButton;
	}
	
	public ViewHolder viewHolder;
	
	public NumberValueSetter(Activity activity, int includeResourceID){
		this.viewHolder = new ViewHolder();
		this.viewHolder.ValueSetter = activity.findViewById(includeResourceID);
	}
	
	public NumberValueSetter(View view, int includeResourceID){
		this.viewHolder = new ViewHolder();
		this.viewHolder.ValueSetter  = view.findViewById(includeResourceID);		
	}
	
	public void Initialise(String label, String valueLabel, String initialValue)
	{
		//this.viewHolder.layout = (LinearLayout)this.viewHolder.ValueSetter.findViewById(R.id.numeric_value_setter_layout);
		this.viewHolder.label = (TextView) this.viewHolder.ValueSetter.findViewById(R.id.numeric_value_setter_label);
		this.viewHolder.value = (TextView) this.viewHolder.ValueSetter.findViewById(R.id.numeric_value_setter_value);
		this.viewHolder.valueLabel = (TextView) this.viewHolder.ValueSetter.findViewById(R.id.numeric_value_setter_value_label);
		this.viewHolder.minusButton =  this.viewHolder.ValueSetter.findViewById(R.id.numeric_value_setter_minus);
		this.viewHolder.plusButton =  this.viewHolder.ValueSetter.findViewById(R.id.numeric_value_setter_plus);
				
		this.viewHolder.label.setText(label);
		this.viewHolder.value.setText(initialValue);
		this.viewHolder.valueLabel.setText(valueLabel);
		
		this.SetActionHandlers();			
	}	
	
	private void SetActionHandlers(){

		this.viewHolder.plusButton.setOnClickListener(new View.OnClickListener() 
    	{
    		public void onClick(View v) 
    		{
    			Integer currentValue = NumberValueSetter.this.getValue();
    			currentValue ++;
    			NumberValueSetter.this.setValue(currentValue);
    		}
    	});	
		
		this.viewHolder.plusButton.setOnLongClickListener(new View.OnLongClickListener() 
    	{
    		public boolean onLongClick(View v) 
    		{
    			Integer currentValue = NumberValueSetter.this.getValue();
    			currentValue += 10;
    			NumberValueSetter.this.setValue(currentValue);
				return true;
    		}
    	});	

		this.viewHolder.minusButton.setOnClickListener(new View.OnClickListener() 
    	{
    		public void onClick(View v) 
    		{
    			Integer currentValue = NumberValueSetter.this.getValue();
    			currentValue --;
    			if(currentValue<0)currentValue=0;
    			NumberValueSetter.this.setValue(currentValue);
    		}
    	});		
		
		this.viewHolder.minusButton.setOnLongClickListener(new View.OnLongClickListener() 
    	{
    		public boolean onLongClick(View v) 
    		{
    			Integer currentValue = NumberValueSetter.this.getValue();
    			currentValue -= 10;
    			if(currentValue<0)currentValue=0;
    			NumberValueSetter.this.setValue(currentValue);
				return true;
    		}
    	});	
		
	}	
	public String getStringValue(){
		return this.viewHolder.value.getText().toString();
	}
	
	public void setValue(String value){
		this.viewHolder.value.setText(value);
	}	
	
	public Integer getValue(){
		return Parser.TryParse(getStringValue(), 0);
	}
	
	public void setValue(Integer value){
		String stringValue = String.valueOf(value);
		this.setValue(stringValue);
	}		
}
