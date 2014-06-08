package com.example.important;

public class Constants {
	public final static int SAMPLING = 44100;
	public static final int BITS_16 = 32768;
	public final static int START_STATE = 1;
	public final static int STOP_STATE = 0;
	public final static String AVAILABLE_SIGNS = "0123456789ABCDEFHG";
//	public final static int DEFAULT_BUFFER_SIZE = 2*8192;
	public final static int DEFAULT_BUFFER_COUNT = 3;
	public final static int DEFAULT_GEN_DURATION = 1;
	public final static int DEFAULT_NUM_SAMPLES = DEFAULT_GEN_DURATION * SAMPLING / 20;
	public final static int DEFAULT_BUFFER_SIZE = 2*DEFAULT_NUM_SAMPLES;
	public final static String START_OF_DATA = "GH";
	public final static String END_OF_DATA = "HG";
	// 0123456789ABCDEFGH = 15 signs
	public final static int[] FREQUENCIES = { 1422, 1575, 1679, 1803, 2321,
			2670, 2974, 3103, 3400, 3609, 3845, 4410, 4700, 4923, 5200, 6400, 7310, 8210 };
//	public final static int[] FREQUENCIES = { 14000, 14100, 14250, 14400, 14550,
//		18200, 18350, 18500, 18650, 18800, 19000, 19200, 19300, 19450, 19600, 20000, 20200, 20300 };
}
