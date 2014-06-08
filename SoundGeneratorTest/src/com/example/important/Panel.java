package com.example.important;

import com.example.sinvoicedemo.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Panel extends SurfaceView implements SurfaceHolder.Callback, PaintObserver {
	private CanvasThread canvasthread;
	private int sample_to_draw[];
	public Panel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Panel(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		getHolder().addCallback(this);
		sample_to_draw = new int[Constants.DEFAULT_NUM_SAMPLES];
		canvasthread = new CanvasThread(getHolder(), this);
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		canvasthread.setRunning(true);
		canvasthread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		canvasthread.setRunning(false);
		while (retry) {
			try {
				canvasthread.join();
				retry = false;
			} catch (InterruptedException e) {
				// we will try it again and again...
			}
		}
	}
	
	public void fillDataToDraw(int sample_to_draw[]){
		this.sample_to_draw = sample_to_draw;
	}
	
	public void onDraw(Canvas canvas) {

		Paint paint = new Paint();

		Bitmap kangoo = BitmapFactory.decodeResource(getResources(),
				R.drawable.kangoo);
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(kangoo, 10, 10, null);
		
		paint.setColor(Color.WHITE);
		for(int i = 0; i < sample_to_draw.length; i++){
			canvas.drawLine(i, sample_to_draw[i], i+1, sample_to_draw[i], paint);
		}
		
		paint.setColor(Color.RED);
		canvas.drawCircle(20, 50, 25, paint);
	}

	@Override
	public void setDataToDraw(int[] data) {
		// TODO Auto-generated method stub
		this.sample_to_draw = data;
		this.invalidate();
	}

}
