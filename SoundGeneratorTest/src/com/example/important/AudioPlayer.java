package com.example.important;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.example.important.SoundGenerator.Observer;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class AudioPlayer {
	private final static String TAG = "AudioPlayer";

	private AudioTrack audiotrack;
	private int state;
	List<Integer> indexesOfSigns;
	private BlockingQueue<Buffer> queueWithData;
	MyObserver observer;
	byte[] audioData;

	public static interface Observed {
		void setObserver(Observer o);

		void stopStatus();
	}

	public AudioPlayer(int sampleRate) {

		int minSize = AudioTrack.getMinBufferSize(sampleRate,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT);

		audiotrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
				AudioFormat.CHANNEL_OUT_MONO, AudioFormat.ENCODING_PCM_16BIT,
				minSize, AudioTrack.MODE_STREAM);

		queueWithData = new LinkedBlockingQueue<Buffer>(4);
		indexesOfSigns = new ArrayList<Integer>();
		state = Constants.STOP_STATE;
	}

	public void play(byte[] audioData, int sizeOfBuffer) {
		audiotrack.write(audioData, 0, sizeOfBuffer);
	}

	public void setIndexesOfSigns(List<Integer> inofsign) {
		MessagesLog.d(TAG, "No kurwa");
		indexesOfSigns = inofsign;
		encodesDataToBuffers(indexesOfSigns);
	}

	public void start() {
		MessagesLog.d(TAG, "Weszlo w start");
		if (state == Constants.STOP_STATE) {
			MessagesLog.d(TAG, "Weszlo w ifa");
			state = Constants.START_STATE;
			int startLength = 0;
			while (state == Constants.START_STATE) {
				Buffer buffer = getBufferFromQueue();
				MessagesLog.d(TAG, "Pobiera");
				if (buffer != null) {
					byte[] data = buffer.getBuffer();
					int sizeOfData = buffer.getBufferSize();
					if (data != null) {
						int len = audiotrack.write(data, 0, sizeOfData);
						if (startLength == 0) {
							audiotrack.play();
						}
						startLength += len;
					} else {
						MessagesLog.d(TAG, "End of data. Stop transmission");
						break;
					}

				} else {
					MessagesLog.e(TAG, "get null data");
					break;
				}
			}

			if (audiotrack != null) {
				audiotrack.pause();
				audiotrack.flush();
				audiotrack.stop();
			}
			state = Constants.STOP_STATE;
			MessagesLog.d(TAG, "end of transferring data");
			observer.setStopStatus();

		}
	}

	public void encodesDataToBuffers(List<Integer> inofsign) {

		for (int index : inofsign) {
			int n = Constants.BITS_16 / 2;
			int totalCount = (Constants.DEFAULT_GEN_DURATION * Constants.SAMPLING) / 1000;
			double per = (Constants.FREQUENCIES[index] / (double) Constants.SAMPLING)
					* 2 * Math.PI;
			double d = 0;
			Buffer buffer = new Buffer();
			byte[] bufferData = new byte[Constants.DEFAULT_BUFFER_SIZE];
			int indexInBuffer = 0;
			for (int i = 0; i < totalCount; ++i) {
				int out = (int) (Math.sin(d) * n) + 128;
				if (indexInBuffer >= Constants.DEFAULT_BUFFER_SIZE - 1) {
					buffer.setBuffer(bufferData);
					buffer.setBufferSize(indexInBuffer);
					setBufferToQueue(buffer);
					bufferData = new byte[Constants.DEFAULT_BUFFER_SIZE];
					indexInBuffer = 0;
				}

				bufferData[indexInBuffer++] = (byte) (out & 0xff);
				bufferData[indexInBuffer++] = (byte) ((out >> 8) & 0xff);

				d += per;

			}

			buffer.setBuffer(bufferData);
			buffer.setBufferSize(indexInBuffer);
			setBufferToQueue(buffer);

			indexInBuffer = 0;
		}

	}

	private void setBufferToQueue(Buffer buffer) {
		try {
			queueWithData.put(buffer);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Buffer getBufferFromQueue() {
		if (queueWithData != null) {
			try {
				return queueWithData.take();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public void setObserver(MyObserver o) {
		observer = o;

	}

	public void stop() {
		if (state == Constants.START_STATE) {
			state = Constants.STOP_STATE;
			queueWithData.clear();
		}
	}
}
