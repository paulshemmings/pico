package razor.android.lib.core.helpers;

import java.io.FileInputStream;
import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class SoundPlayer {
	
	public static interface IStateChangedListener {
		public void stateStarted(SoundPlayer soundPlayer);
		public void stateStopped(SoundPlayer soundPlayer);
	}
	
	private enum StateEnum {
		Uninitialized,
		Idle,
		Initialized,
		Prepared,
		Started,
		PlaybackComplete,
		Stopped,
		Preparing,
		Paused,
		Error,
		End
	}
	
	private StateEnum state = StateEnum.Uninitialized;
	
	private MediaPlayer mediaPlayer;
	private String filesDir;
	private FileInputStream file;
	
//	private boolean playing;
	
//	private boolean set;
	
	private IStateChangedListener stateChangedListener;
	
	private int duration;
	
	private String filenameStarted;
	
	public int getDuration() { 
		return this.duration; 
	}
	
	public void setStateChangedListener(IStateChangedListener playStarted) { 
		this.stateChangedListener = playStarted; 
	}
	
	private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
		@Override
		public void onPrepared(MediaPlayer mp) {
			state = StateEnum.Prepared;			
			mp.start();
			state = StateEnum.Started;
			
			duration = mp.getDuration();
			
			if (stateChangedListener != null) {				
				stateChangedListener.stateStarted(SoundPlayer.this);
			}			
		}
	};
	
	private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {		
		@Override
		public void onCompletion(MediaPlayer mp) {
			if(mediaPlayer!=null){
				state = StateEnum.PlaybackComplete;
				mediaPlayer.stop();
				state = StateEnum.Stopped;
				
				if (stateChangedListener != null) {
					stateChangedListener.stateStopped(SoundPlayer.this);
			}
			}
		}
	};
	
	private MediaPlayer.OnErrorListener errorListener = new MediaPlayer.OnErrorListener() {
		@Override
		public boolean onError(MediaPlayer mp, int what, int extra) {
			state = StateEnum.Error;
			return false;
		}
	};
	
	public SoundPlayer(Context context) {
		filesDir = context.getFilesDir().getPath();		
		state = StateEnum.Idle;
	}
	
	public void reset() {
		filenameStarted = null;
	}
	
	public synchronized boolean isPlaying(){
		return mediaPlayer == null ? false : mediaPlayer.isPlaying();
	}
	
	public synchronized void seekTo(int msec){
		if(mediaPlayer != null) {
			mediaPlayer.seekTo(msec);
		}
	}
	
	public synchronized void start(){
		if(mediaPlayer != null) {
			mediaPlayer.start();		
			state = StateEnum.Started;
		}
	}
	
	public synchronized void start(String filename) throws IOException {
		if (filename == null || filename.length() <= 0) return;
		
		// Check for states we can't call 
		if (
			state == StateEnum.PlaybackComplete || 
			state == StateEnum.Preparing ||
			state == StateEnum.Error
				) {
			return;
		}
		
		if (filenameStarted == null || !filenameStarted.equalsIgnoreCase(filename)) {
			state = StateEnum.Uninitialized;
		}
		
		if (state == StateEnum.Uninitialized) {						
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setOnPreparedListener(preparedListener);		
			mediaPlayer.setOnCompletionListener(completionListener);
			mediaPlayer.setOnErrorListener(errorListener);
			
			state = StateEnum.Idle;

			String path = filesDir + "/" + filename;
			file = new FileInputStream(path);
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setDataSource(file.getFD());		
			
			state = StateEnum.Initialized;
		}
		
		filenameStarted = filename;
		
		if (state == StateEnum.Initialized || state == StateEnum.Stopped) {
			mediaPlayer.prepareAsync();
			state = StateEnum.Preparing;
		}
	}
	
	public synchronized void stop() {
		// Check the states we can't call stop on.
		if (
				state == StateEnum.Uninitialized ||
				state == StateEnum.Initialized ||
				state == StateEnum.Idle ||				
				state == StateEnum.Stopped			
				) {
			return;
		}
		if(mediaPlayer != null) {		
			mediaPlayer.stop();
			state = StateEnum.Uninitialized;
			
			if (stateChangedListener != null) {
				stateChangedListener.stateStopped(SoundPlayer.this);
			}	
		}
	}
	
	public synchronized void pause(){
		// Check the states we can't call stop on.
		if (
				state == StateEnum.Uninitialized ||
				state == StateEnum.Initialized ||
				state == StateEnum.Idle ||				
				state == StateEnum.Paused			
				) {
			return;
		}
		if(mediaPlayer != null) {		
			mediaPlayer.pause();
			state = StateEnum.Paused;
		}
	}
	
	public int getCurrentPosition() {
		return mediaPlayer == null ? 0 : mediaPlayer.getCurrentPosition();
	}
}
