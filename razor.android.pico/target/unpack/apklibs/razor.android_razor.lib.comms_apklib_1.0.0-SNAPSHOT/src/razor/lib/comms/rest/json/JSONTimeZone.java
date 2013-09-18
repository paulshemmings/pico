package razor.lib.comms.rest.json;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONTimeZone extends razor.lib.comms.model.TimeZone {
	
	public JSONTimeZone(JSONObject jo) throws JSONException {				
		setId(jo.getInt("ID"));
		setAbbr(jo.getString("Abbreviation"));
		setGmtOffset(jo.getLong("GMTOffsetMinutes"));
		setName(jo.getString("Name"));
	}
}
