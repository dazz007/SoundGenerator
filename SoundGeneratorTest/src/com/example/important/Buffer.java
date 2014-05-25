package com.example.important;

public class Buffer {
	private byte[] buffer;
	private int bufferSize;

	public Buffer() {

	}

	public Buffer(byte[] buf, int bufSize) {
		buffer = buf;
		bufferSize = bufSize;
	}

	public void setBuffer(byte[] buf) {
		buffer = buf;
	}

	public void setBufferSize(int bufSize) {
		bufferSize = bufSize;
	}

	public byte[] getBuffer() {
		return buffer;
	}

	public int getBufferSize() {

		return bufferSize;
	}
}
