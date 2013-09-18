package razor.android.lib.core.helpers;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class StringHelper {

	public static String format(Context context, String format, Object ... objects){
		List<String> strings = new ArrayList<String>();
		for(int i=0;i<objects.length;i++){
			if(objects[i].getClass().equals(int.class) || objects[i].getClass().equals(Integer.class)){
				strings.add((String) context.getResources().getString((Integer) objects[i]));
			}else if(objects[i].getClass().equals(String.class)){
				strings.add((String) objects[i]);
			} else{
				strings.add(String.valueOf(objects[i]));
			}
		}
		return String.format(format, strings.toArray());
	}
	
	public static String format(Context context, Integer formatResourceId, Object ... objects){
		return format(context,context.getResources().getString((Integer)formatResourceId),objects);
	}
	
}
