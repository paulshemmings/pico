package razor.android.pico.handlers;

import razor.android.pico.providers.PicoProvider;
import razor.lib.comms.pico.R;
import razor.lib.comms.replyhandlers.ICommReplyHandler;
import razor.android.lib.core.handlers.AbstractHandler;
import razor.lib.models.messages.ICommReply;
import razor.lib.models.messages.ICommRequest;
import razor.lib.models.messages.ModelReply;
import razor.lib.models.messages.pico.LoadPhotoRequest;
import android.content.Context;
import android.graphics.Bitmap;

public class PhotoHandler  extends AbstractHandler implements ICommReplyHandler   {

	/**
	 * published interface
	 * @author phemmings
	 *
	 */
	
	public interface onLoadPhotoListener{
		void onPhotoLoaded(Long eventId, Bitmap bm);
		void onPhotoFailed(Long eventId, String errorMessage);
	}
	
	/*
	 * properties
	 */
	
	private onLoadPhotoListener eventListener = null;
	
	/**
	 * Constructor
	 * @param parentContext
	 */
	
	public PhotoHandler(Context parentContext) {
		super(parentContext);
	}
	
	/**
	 * Load a photo
	 * @param listener
	 * @param photoId
	 */
	
	public void getPhoto(onLoadPhotoListener listener, Long photoId){
		this.eventListener = listener;
		
		LoadPhotoRequest request = new LoadPhotoRequest();
		request.setPhotoId(photoId);
		
		PicoProvider.instance().handleRequest(R.string.loadPhotoRequest, this, request);
	}
	
	/**
	 * Handle Comms Reply
	 */

	@Override
	public void onCommsReply(ICommRequest request, ICommReply reply) {
		
		if(this.eventListener!=null){
			if(reply.getMessageType() == R.string.loadPhotoRequest){
				LoadPhotoRequest photoLoadRequest = (LoadPhotoRequest)request;
				if(reply.getResponseCode()==0){
					try{
						ModelReply<Bitmap> modelReply = (ModelReply<Bitmap>) reply;
						LoadPhotoRequest photoRequest = (LoadPhotoRequest) request; 
						this.eventListener.onPhotoLoaded(photoRequest.getPhotoId(), modelReply.getModel());
					}catch(Exception ex){
						this.eventListener.onPhotoFailed(photoLoadRequest.getPhotoId(), ex.getMessage());
					}
				}else{
					this.eventListener.onPhotoFailed(photoLoadRequest.getPhotoId(), "failed");
				}
			}
		}
	}

}
