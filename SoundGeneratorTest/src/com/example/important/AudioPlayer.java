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
	private Panel panel;
	private AudioTrack audiotrack;
	private int state;
	List<Integer> indexesOfSigns;
	private ArrayList<Buffer> queueWithDataAL;
	// private BlockingQueue<Buffer> queueWithData;
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
		queueWithDataAL = new ArrayList<Buffer>();
		// queueWithData = new LinkedBlockingQueue<Buffer>(4);
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
				Buffer buffer = getBufferFromQueueAL();
				MessagesLog.d(TAG, "Pobiera");
				if (buffer != null) {
					byte[] data = buffer.getBuffer();
					int sizeOfData = buffer.getBufferSize();
					int[] dataValues = buffer.getBufferValues();
					if (data != null) {
						int len = audiotrack.write(data, 0, sizeOfData);
						if (startLength == 0) {
							audiotrack.play();
//							state = Constants.STOP_STATE;
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
//			int totalCount = (Constants.DEFAULT_GEN_DURATION * Constants.SAMPLING) / 1000;
			int numSamples = Constants.DEFAULT_NUM_SAMPLES;
			double per = (Constants.FREQUENCIES[index] / (double) Constants.SAMPLING)
					* 2 * Math.PI;
			double d = 0;
			Buffer buffer = new Buffer();
			byte[] bufferData = new byte[Constants.DEFAULT_BUFFER_SIZE];
			int[] bufferValues = new int[Constants.SAMPLING]; 
			int indexInBuffer = 0;
			int ramp = numSamples / 20;
			for (int i = 0; i < numSamples; ++i) {
//				int out = (int) (Math.sin(d) * n) + 128;
				
				
				double out = (double) (Math.sin(Constants.FREQUENCIES[index] * 2 * Math.PI  * i / Constants.SAMPLING) );
				if (indexInBuffer >= Constants.DEFAULT_BUFFER_SIZE - 1) {
					buffer.setBuffer(bufferData);
					buffer.setBufferSize(indexInBuffer);
					buffer.setBufferValues(bufferValues);
					buffer.setBufferValuesSize(i);
					setBufferToQueueAL(buffer);
					bufferData = new byte[Constants.DEFAULT_BUFFER_SIZE];
					indexInBuffer = 0;
				}
				
				
				final short val;
				if(i < ramp){
					val = (short) ((out * n * i / ramp));					
				}else if(i < numSamples - ramp){
					val = (short) ((out * n));
				}else{
					val = (short) ((out * n * (numSamples-i)/ramp));
				}
				bufferValues[i] = val;
				bufferData[indexInBuffer++] = (byte) (val & 0x00ff);
				bufferData[indexInBuffer++] = (byte) ((val & 0xff00) >>> 8);

				d += per;

			}

			buffer.setBuffer(bufferData);
			buffer.setBufferSize(indexInBuffer);
			buffer.setBufferValues(bufferValues);
			buffer.setBufferValuesSize(indexInBuffer/2);
			setBufferToQueueAL(buffer);

			indexInBuffer = 0;
		}

	}

//	private void setBufferToQueue(Buffer buffer) {
//		try {
//			queueWithData.put(buffer);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	private void setBufferToQueueAL(Buffer buffer) {
		queueWithDataAL.add(buffer);
	}

//	private Buffer getBufferFromQueue() {
//		if (queueWithData != null) {
//			try {
//				return queueWithData.take();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//		return null;
//	}

	private Buffer getBufferFromQueueAL() {

		if (queueWithDataAL.size() > 0) {
			Buffer buffer = queueWithDataAL.get(0);
			queueWithDataAL.remove(0);
			return buffer;

		}
		return null;

	}

	public void setObserver(MyObserver o) {
		observer = o;

	}

	public void stop() {
		if (state == Constants.START_STATE) {
			state = Constants.STOP_STATE;
			queueWithDataAL.clear();
//			queueWithData.clear();
		}
	}
}
