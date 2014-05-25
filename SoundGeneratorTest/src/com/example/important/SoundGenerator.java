package com.example.important;

import java.util.ArrayList;
import java.util.List;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class SoundGenerator implements MyObserver {
	private final static String TAG = "SoundGenerator";
	private int state;
	private String textToPlay;
	private AudioPlayer player;
	private Thread threadPlayer;
	private Thread threadEncoding;

	public SoundGenerator(int sampleRate) {

		player = new AudioPlayer(sampleRate);
		player.setObserver(this);
		state = Constants.STOP_STATE;
	}

	public static interface Observer {
		public void update();
	}

	public void setTextToEncode(String text) {
		textToPlay = text;
	}

	public List<Integer> encodeText() {
		List<Integer> indexesOfSigns = new ArrayList<Integer>();
		// add start of data
		for (int i = 0; i < Constants.START_OF_DATA.length(); i++) {
			int index = Constants.AVAILABLE_SIGNS
					.indexOf(Constants.START_OF_DATA.charAt(i));
			indexesOfSigns.add(index);
		}

		for (int i = 0; i < textToPlay.length(); i++) {
			int index = Constants.AVAILABLE_SIGNS.indexOf(textToPlay.charAt(i));
			indexesOfSigns.add(index);
		}

		for (int i = 0; i < Constants.END_OF_DATA.length(); i++) {
			int index = Constants.AVAILABLE_SIGNS.indexOf(Constants.END_OF_DATA
					.charAt(i));
			indexesOfSigns.add(index);
		}

		return indexesOfSigns;
	}

	public void start() {
		if (state == Constants.STOP_STATE) {
			state = Constants.START_STATE;
			List<Integer> indexesOfSigns = encodeText();
			player.setIndexesOfSigns(indexesOfSigns);
			MessagesLog.d(TAG, "Weszlo w start");
			threadPlayer = new Thread() {
				@Override
				public void run() {
					player.start();
				}
			};
			if (threadPlayer != null) {
				threadPlayer.start();
			}
		}
	}

	@Override
	public void setStopStatus() {
		// TODO Auto-generated method stub
		if (state == Constants.START_STATE) {
			state = Constants.STOP_STATE;
			MessagesLog.d(TAG, "sending data is over");
			player.stop();
			if (threadPlayer != null) {
				try {
					threadPlayer.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					threadPlayer = null;
				}
			}
		}
	}

}
