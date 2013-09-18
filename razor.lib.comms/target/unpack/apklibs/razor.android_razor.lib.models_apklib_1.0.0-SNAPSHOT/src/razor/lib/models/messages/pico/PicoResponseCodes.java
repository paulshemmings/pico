package razor.lib.models.messages.pico;

public class PicoResponseCodes {

	public static final long success = 0;
	public static final long invalid_action_verb = 1;
	public static final long invalid_parameter = 101;
	public static final long too_many_events_uploaded_in_time_frame = 2;
	public static final long failed_to_open_request_stream = 3;
	public static final long failed_to_retrieve_complete_event_details = 4;
	public static final long failed_to_build_list_request_from_http_request = 5;
	public static final long servlet_had_unknown_error_building_event_list = 6;
	public static final long insert_new_event_failed = 7;
	public static final long failed_to_get_last_creator_events = 8;
	public static final long failed_to_get_event_by_id = 9;
	public static final long no_event_with_id_found = 10;	
	public static final long failed_to_instantiate_datastore = 11;
	public static final long failed_to_build_event_from_entity = 12;
	public static final long failed_to_build_event_list = 13;

}
