package razor.android.lib.core.holders;

import java.util.ArrayList;
import java.util.List;

import razor.android.lib.core.R;
import razor.android.lib.core.adapters.CoreViewModelSimpleAdapter;
import razor.android.lib.core.interfaces.ICoreViewModel;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;

public class ViewModelSearchViewHolder implements OnItemClickListener, OnItemSelectedListener, TextWatcher{
	
	private AutoCompleteTextView institutionSearch = null;
	private CoreViewModelSimpleAdapter adapter = null;
	private View parentView = null;
	private OnSearchSelectedListener listener = null;
	private String currentSearchText = null;
    public boolean itemClickCalled;
    public int itemClickPosition;
    public boolean itemSelectedCalled;
    public int itemSelectedPosition;
    public boolean nothingSelectedCalled;	
    
    public interface OnSearchSelectedListener{
    	void OnSearchSelected(ICoreViewModel selectedValue);
    	void OnSearchChanged(String searchString);
    }
		
	public ViewModelSearchViewHolder(View view, OnSearchSelectedListener listener){
		this.parentView = view;
		this.listener = listener;
		this.institutionSearch = (AutoCompleteTextView) view.findViewById(R.id.viewmodel_autocomplete_search);
		this.institutionSearch.addTextChangedListener(this);
		this.institutionSearch.setOnItemClickListener(this);
		this.institutionSearch.setOnItemSelectedListener(this);		
		this.resetItemListeners();
	}	
	
    public AutoCompleteTextView getTextView() {
        return this.institutionSearch;
    }
    
    public String getCurrentSearchText(){
    	return this.currentSearchText;
    }
    
    public void resetItemListeners() {
        this.itemClickCalled = false;
        this.itemClickPosition = -1;
        this.itemSelectedCalled = false;
        this.itemSelectedPosition = -1;
        this.nothingSelectedCalled = false;
    }    
    
    public void setAdapter(LayoutInflater inflater, List<ICoreViewModel> models){
    	List<String> titleArray = new ArrayList<String>();
    	for(ICoreViewModel model:models){
    		titleArray.add(model.getTitle());
    	}
        this.adapter = new CoreViewModelSimpleAdapter(this.parentView.getContext(), models);
        this.institutionSearch.setAdapter(this.adapter);
    }
    
    public void setAdapter(CoreViewModelSimpleAdapter adapter){
    	this.adapter = adapter;
    	this.institutionSearch.setAdapter(this.adapter);
    }

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.itemSelectedCalled = true;
        this.itemSelectedPosition = position;		
        if( this.listener != null){
        	this.currentSearchText = this.adapter.getItem(position).getTitle();
        	this.listener.OnSearchSelected(this.adapter.getItem(position));
        }
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		this.nothingSelectedCalled = true;		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        this.itemClickCalled = true;
        this.itemClickPosition = position;		
	}

	@Override
	public void afterTextChanged(Editable s) {
		if( this.listener != null){
			SpannableStringBuilder t = (SpannableStringBuilder) s;
			this.currentSearchText = t.toString();
			this.listener.OnSearchChanged(t.toString());
		}
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
	}	
}
