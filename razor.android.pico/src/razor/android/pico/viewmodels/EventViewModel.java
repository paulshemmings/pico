package razor.android.pico.viewmodels;

import java.io.Serializable;

import razor.android.lib.core.interfaces.ICoreViewModel;
import razor.lib.models.pico.EventDTO;

public class EventViewModel extends ModelContainer<EventDTO> implements ICoreViewModel, Serializable {
	
	//private Bitmap image = null;
	private String imagePath = null;
	private String fullSizedImagePath = null;
	private String mapMarkerId = null;
	
	public Long getID() {
		return this.getModel().getEventId();
	}
	public String getTitle() {
		return this.getModel().getTitle();
	}
	
	@Override
	public String[] getDetails() {
		return null;
	}

	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getFullSizedImagePath() {
		return fullSizedImagePath;
	}
	public void setFullSizedImagePath(String fullSizedImagePath) {
		this.fullSizedImagePath = fullSizedImagePath;
	}
	public String getMapMarkerId() {
		return mapMarkerId;
	}
	public void setMapMarkerId(String mapMarkerId) {
		this.mapMarkerId = mapMarkerId;
	}
}
