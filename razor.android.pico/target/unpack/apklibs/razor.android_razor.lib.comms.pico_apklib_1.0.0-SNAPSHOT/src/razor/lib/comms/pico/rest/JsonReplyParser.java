package razor.lib.comms.pico.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import razor.lib.comms.rest.responses.ICommRestResponse;
import razor.android.lib.core.helpers.LogHelper;

import com.google.gson.Gson;

public class JsonReplyParser<T>{
	
	public JsonReplyParser(){
	}
	
	@SuppressWarnings("unchecked")
	public T buildContainedModel(ICommRestResponse<String> restResponse, String container, Class<T> classType){
		T model = null;
		try
		{
			JSONObject jsonObject = new JSONObject(restResponse.getResponseContent());
			JSONObject jsonHolder = jsonObject.getJSONObject(container);
			String objectString = jsonHolder.toString();
			model = (T) new Gson().fromJson(objectString,  classType );
		}
		catch(Exception ex){
			LogHelper.e("RestReplyBuilder.buildContainedObject",ex.getMessage());
		}
		return model;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buildContainedList(ICommRestResponse<String> restResponse, String container, Class<T> classType){
		List<T> list = new ArrayList<T>();
		try
		{
			JSONObject jsonObject = new JSONObject(restResponse.getResponseContent());
			JSONArray jsonArray = jsonObject.getJSONArray(container);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonArrayItem = jsonArray.getJSONObject(i);								
				T item = (T)  new Gson().fromJson(jsonArrayItem.toString(), classType);
				list.add(item);							
			}
		}
		catch(Exception ex){
			LogHelper.e("RestReplyBuilder.buildContainedList",ex.getMessage());
		}
		return list;
	}	
	
}
