package razor.android.lib.core.wrappers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import razor.android.lib.core.helpers.LogHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;


public class ImageWrapper {

	public ImageWrapper(){
	}
	
	public byte[] toByteArray(Context context, String sourcePath) throws IOException{
		Uri outputFileUri = Uri.fromFile(new File(sourcePath) ); 
		InputStream is = context.getContentResolver().openInputStream(outputFileUri);		
		return this.toByteArray(is);
	}
	
	public byte[] toByteArray(InputStream is) throws IOException {
		
		int len;
		int size = 1024;
		byte[] buf;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		buf = new byte[size];
		while ((len = is.read(buf, 0, size)) != -1){
			bos.write(buf, 0, len);
		}
		
		buf = bos.toByteArray();
		return buf;
	}		
	
	public Bitmap resizeImage(Bitmap bm, int newHeight){
		
		// calculate current dimensions

		int height = bm.getHeight();
		int width = bm.getWidth();
		float scaler = ((float) newHeight) / height;

		// build scaler
		
		Matrix matrix = new Matrix();
		matrix.postScale(scaler, scaler);
		
		// build resized bitmap
		
		return Bitmap.createBitmap(bm, 0, 0,width, height, matrix, true);		

	}
	
	public Bitmap resizeImage(InputStream is, int newHeight){
	
		BitmapFactory.Options options=new BitmapFactory.Options();
		Bitmap bm = BitmapFactory.decodeStream(is,null,options);

		return this.resizeImage(bm, newHeight);		
	}
	
	public Bitmap resizeImage(Context context, String sourcePath, int newHeight) throws IOException{

		// get input stream from source
		
		Uri outputFileUri = Uri.fromFile(new File(sourcePath) ); 
		InputStream is = context.getContentResolver().openInputStream(outputFileUri);
		
		// create a new resized bitmap from the source
		
		Bitmap resizedBitmap = this.resizeImage(is, newHeight);

		// return the bitmap
		
		return resizedBitmap;
	}

	public String resizeImage(Context context, String sourcePath, String destinationName, int newHeight) throws IOException{

		// get the resized bitmap
		
		Bitmap resizedBitmap = this.resizeImage(context, sourcePath, newHeight);		

		// save bitmap
		
		return this.saveBitmapAs(context, resizedBitmap, destinationName);

	}
	
	public String saveBitmapAs(Context context, Bitmap bitmap, String destinationName) throws IOException{
		
		// remove destination if already present
		
		// File photo= new File(Environment.getExternalStorageDirectory(), destinationName);
		File photo= new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), destinationName);
		
		if (photo.exists()) {
			photo.delete();
		}
		
		// save new bitmap to destination file
		
		OutputStream os = context.getContentResolver().openOutputStream(Uri.fromFile(photo));
		bitmap.compress(Bitmap.CompressFormat.PNG, 90, os);
		os.close();
		
		// return path to new destination

		return Uri.fromFile(photo).getPath();		
		
	}
	
	public void tellMediaScannerAboutImage(final Context context, final String path){
		Thread thread = new Thread(new Runnable(){
			public void run() {
				 MediaScannerConnection.scanFile(
						 context,
				         new String[] {path}, 
				         null,
				         new MediaScannerConnection.OnScanCompletedListener() {
							 public void onScanCompleted(String path, Uri uri) {
								 LogHelper.i("ExternalStorage", "Scanned " + path + ":");
								 LogHelper.i("ExternalStorage", "-> uri=" + uri);
							 }
						 });
				}		
			});
		
		thread.start();
	}
}
