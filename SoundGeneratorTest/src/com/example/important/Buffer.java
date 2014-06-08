package com.example.important;

public class Buffer {
	private byte[] buffer;
	private int bufferSize;
	private int [] bufferValues;
	private int bufferValuesSize;
	public Buffer() {

	}

	public Buffer(byte[] buf, int bufSize, int[] buffValues, int buffValuesSize) {
		buffer = buf;
		bufferSize = bufSize;
		setBufferValues(buffValues);
		setBufferValuesSize(buffValuesSize);
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

	public int [] getBufferValues() {
		return bufferValues;
	}

	public void setBufferValues(int [] bufferValues) {
		this.bufferValues = bufferValues;
	}

	public int getBufferValuesSize() {
		return bufferValuesSize;
	}

	public void setBufferValuesSize(int bufferValuesSize) {
		this.bufferValuesSize = bufferValuesSize;
	}
}
