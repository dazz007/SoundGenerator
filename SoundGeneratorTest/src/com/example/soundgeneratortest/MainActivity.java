package com.example.soundgeneratortest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.example.important.Constants;
import com.example.important.MessagesLog;
import com.example.important.SoundGenerator;
import com.example.sinvoicedemo.R;

public class MainActivity extends Activity {
    private final static String TAG = "MainActivity";
    private SoundGenerator soundgen;
//    private final static String CODEBOOK = "123456789ABCDEF";
    private final static String CODEBOOK = "12345";
    private Handler mHanlder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView playTextView = (TextView) findViewById(R.id.playtext);
        TextView recognisedTextView = (TextView) findViewById(R.id.regtext);
        
        soundgen = new SoundGenerator(Constants.SAMPLING);
//        mHanlder = new RegHandler(recognisedTextView);

        Button playStart = (Button) this.findViewById(R.id.start_play);
        playStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
//            	 String text = genText(7);
                String text = "123456789234021389638726387623875645463548356845436576546537654836946493864";
                MessagesLog.d(TAG, "Kliknalem");
                playTextView.setText(text);
                soundgen.setTextToEncode(text);
                soundgen.start();
            }
        });

//        Button playStop = (Button) this.findViewById(R.id.stop_play);
//        playStop.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                mSinVoicePlayer.stop();
//            }
//        });

//        Button recognitionStart = (Button) this.findViewById(R.id.start_reg);
//        recognitionStart.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                mRecognition.start();
//            }
//        });
//
//        Button recognitionStop = (Button) this.findViewById(R.id.stop_reg);
//        recognitionStop.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                mRecognition.stop();
//            }
//        });
//    }
//
//    private String genText(int count) {
//        StringBuilder sb = new StringBuilder();
//        int pre = 0;
//        while (count > 0) {
//            int x = (int) (Math.random() * MAX_NUMBER + 1);
//            if (Math.abs(x - pre) > 0) {
//                sb.append(x);
//                --count;
//                pre = x;
//            }
//        }
//
//        return sb.toString();
//    }
//
//    private static class RegHandler extends Handler {
//        private StringBuilder mTextBuilder = new StringBuilder();
//        private TextView mRecognisedTextView;
//
//        public RegHandler(TextView textView) {
//            mRecognisedTextView = textView;
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//            case MSG_SET_RECG_TEXT:
//                char ch = (char) msg.arg1;
//                mTextBuilder.append(ch);
//                if (null != mRecognisedTextView) {
//                    mRecognisedTextView.setText(mTextBuilder.toString());
//                }
//                break;
//
//            case MSG_RECG_START:
//                mTextBuilder.delete(0, mTextBuilder.length());
//                break;
//
//            case MSG_RECG_END:
//                LogHelper.d(TAG, "recognition end");
//                break;
//            }
//            super.handleMessage(msg);
//        }
//    }
//
//    @Override
//    public void onRecognitionStart() {
//        mHanlder.sendEmptyMessage(MSG_RECG_START);
//    }
//
//    @Override
//    public void onRecognition(char ch) {
//        mHanlder.sendMessage(mHanlder.obtainMessage(MSG_SET_RECG_TEXT, ch, 0));
//    }
//
//    @Override
//    public void onRecognitionEnd() {
//        mHanlder.sendEmptyMessage(MSG_RECG_END);
//    }
//
//    @Override
//    public void onPlayStart() {
//        LogHelper.d(TAG, "start play");
//    }
//
//    @Override
//    public void onPlayEnd() {
//        LogHelper.d(TAG, "stop play");
//    }

}
}
