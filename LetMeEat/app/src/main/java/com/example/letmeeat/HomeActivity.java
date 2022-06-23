package com.example.letmeeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;

import javax.xml.transform.Result;

public class HomeActivity extends AppCompatActivity  {

    private MyGLSurfaceView mGLSurfaceView;;
    public static final String EXTRA_IS_BUTTON_EASY = "isButtonEasy";
    public static final String EXTRA_IS_BUTTON_HARD = "isButtonHard";
    public static boolean levelE;
    public static boolean levelH;
    private TextView scoreL;
    private int score = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        levelE = i.getBooleanExtra(EXTRA_IS_BUTTON_EASY,false);
        levelH = i.getBooleanExtra(EXTRA_IS_BUTTON_HARD,false);
        setContentView(R.layout.activity_home);

        mGLSurfaceView = findViewById(R.id.MyGLSurfaceView);
        scoreL = findViewById(R.id.scoreL);
        scoreL.setText("Score : 0");
        mGLSurfaceView.requestFocus();
        mGLSurfaceView.setFocusableInTouchMode(true);

    }

    // this method is called in the MyGLSurfaceView class to update the text value
    public void updateText(final String text) {
        runOnUiThread(new Runnable() {
            public void run(){
                TextView txtView = (TextView)findViewById(R.id.scoreL);
                txtView.setText(text);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mGLSurfaceView = findViewById(R.id.MyGLSurfaceView);
        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGLSurfaceView = findViewById(R.id.MyGLSurfaceView);
        mGLSurfaceView.onPause();
    }
}



