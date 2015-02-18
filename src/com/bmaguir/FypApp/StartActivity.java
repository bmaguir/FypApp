package com.bmaguir.FypApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;

public class StartActivity extends Activity {
    private static final String TAG = "debugStartActivity";
    private UnityPlayer m_UnityPlayer;
    SpeechRecognizer mSpeechRecognizer;
    Intent mSpeechRecognizerIntent;
    boolean mIsListening;
    public static Context context;
    private String direction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getWindow().setFormat(PixelFormat.RGB_565);
        setContentView(R.layout.start_activity);


        m_UnityPlayer = new UnityPlayer(this);
        int glesMode = m_UnityPlayer.getSettings().getInt("gles_mode", 1);
        boolean trueColor8888 = false;
        m_UnityPlayer.init(glesMode, trueColor8888);


        // Add the Unity view
        FrameLayout layout = (FrameLayout) findViewById(R.id.frameLayout);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        layout.addView(m_UnityPlayer, 0, lp);
        m_UnityPlayer.resume();

        //Speech recognition code
        mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                this.getPackageName());
        SpeechRecognitionListener listener = new SpeechRecognitionListener();
        mSpeechRecognizer.setRecognitionListener(listener);
    }

    public String startFunc(){
        //Log.i("test","-----------------test");
        String temp = direction;
        direction = "null";
        return temp;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        m_UnityPlayer.windowFocusChanged(hasFocus);
    }


    public void leftClick(View v){
        //Toast.makeText(this, "LEFT", Toast.LENGTH_SHORT).show();
        direction = "left";
    }

    public void rightClick(View v){
        //Toast.makeText(this, "RIGHT", Toast.LENGTH_SHORT).show();
        direction = "right";
    }

    public void backClick(View v){
        //Toast.makeText(this, "BACK", Toast.LENGTH_SHORT).show();
        direction = "back";
    }

    public void forwardClick(View v){
        //Toast.makeText(this, "FORWARD", Toast.LENGTH_SHORT).show();
        direction = "forward";
    }
    public void speakClick(View v){
        if (!mIsListening)
        {
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        }
    }

    protected class SpeechRecognitionListener implements RecognitionListener
    {
        @Override
        public void onBeginningOfSpeech()
        {
            Log.d(TAG, "onBeginingOfSpeech");
        }

        @Override
        public void onBufferReceived(byte[] buffer)
        {

        }

        @Override
        public void onEndOfSpeech()
        {
            Log.d(TAG, "onEndOfSpeech");
        }

        @Override
        public void onError(int error)
        {
            // mSpeechRecognizer.startListening(mSpeechRecognizerIntent);

            Log.d(TAG, "error = " + error);
        }

        @Override
        public void onEvent(int eventType, Bundle params)
        {

        }

        @Override
        public void onPartialResults(Bundle partialResults)
        {

        }

        @Override
        public void onReadyForSpeech(Bundle params)
        {
            Log.d("User_Tag", "onReadyForSpeech"); //$NON-NLS-1$
            Toast.makeText(getApplicationContext(), "SPEAK", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onResults(Bundle results)
        {
            Log.d(TAG, "onResults"); //$NON-NLS-1$
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String best = matches.get(0);
            Toast.makeText(getApplicationContext(),"Results = " + best, Toast.LENGTH_LONG).show();
            // matches are the return values of speech recognition engine
            // Use these values for whatever you wish to do
        }

        @Override
        public void onRmsChanged(float rmsdB)
        {

        }

    }

}