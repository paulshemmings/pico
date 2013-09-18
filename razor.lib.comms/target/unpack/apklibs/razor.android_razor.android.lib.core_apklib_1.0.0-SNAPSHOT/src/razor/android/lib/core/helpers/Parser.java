package razor.android.lib.core.helpers;

public class Parser {

	public static Integer TryParse(String value, Integer defaultValue){
		try
		{
			return Integer.parseInt(value.trim());
		}
		catch(Exception ex){
			return 0;
		}
	}
}
