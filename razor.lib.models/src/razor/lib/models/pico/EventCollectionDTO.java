package razor.lib.models.pico;

import java.io.Serializable;
import java.util.ArrayList;

import razor.lib.models.messages.pico.EventLoadRequest;

import com.google.gson.annotations.SerializedName;

public class EventCollectionDTO extends ArrayList<EventDTO> implements Serializable {
}
