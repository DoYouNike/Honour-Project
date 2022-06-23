package com.example.letmeeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
/*
 This is the main menu activity
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mPlay,  mInstruction;
    MediaPlayer bGMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mPlay = findViewById(R.id.play);

        mInstruction = findViewById(R.id.instruction);

        mPlay.setOnClickListener(this);

        mInstruction.setOnClickListener(this);

        // the following code is used to set the background music
        bGMusic = MediaPlayer.create(this, R.raw.song);
        bGMusic.setLooping(true);
        bGMusic.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                startActivity(new Intent(this, LevelSelect.class));
                finish();
                break;

            case R.id.instruction:
                startActivity(new Intent(this, Instruction.class));
                finish();
                break;
        }
    }
}
