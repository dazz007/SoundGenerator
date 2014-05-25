package com.example.important;

import android.util.Log;

public class MessagesLog {
	private static final String ROOT_TAG = "SoundGen";

	public static final int d(String classTag, String privateTag, String msg) {
		return Log.d(String.format("%s %s %s", ROOT_TAG, classTag, privateTag),
				msg);
	}

	public static final int d(String classTag, String msg) {
		return d(classTag, "", msg);
	}

	public static final int e(String classTag, String privateTag, String msg) {
		return Log.e(String.format("%s %s %s", ROOT_TAG, classTag, privateTag),
				msg);
	}

	public static final int e(String classTag, String msg) {
		return e(classTag, "", msg);
	}

}
