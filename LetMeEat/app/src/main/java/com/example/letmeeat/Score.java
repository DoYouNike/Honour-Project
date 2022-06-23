package com.example.letmeeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

// class to using the textview display the game over page
public class Score extends AppCompatActivity {
    boolean easy = HomeActivity.levelE;
    boolean hard = HomeActivity.levelH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        TextView scoreL = (TextView) findViewById(R.id.scoreL);
        TextView HScoreL = (TextView) findViewById(R.id.HScoreL);

        int score = getIntent().getIntExtra("SCORE", 0);
        scoreL.setText(score + "");

        SharedPreferences settings = getSharedPreferences("HIGH_SCORE", Context.MODE_PRIVATE);
        int highScore = settings.getInt("HIGH_SCORE", 0);

        //check is the score player have is bigger than the previous highest score or not
        if (score > highScore) {
            HScoreL.setText("High Score : " + score);

            // Update High Score
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("HIGH_SCORE", score);
            editor.commit();

        } else {
            HScoreL.setText("Highest Score : " + highScore);

        }


    }


    // create the button for tryAgain
    public void tryAgain(View view) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra(HomeActivity.EXTRA_IS_BUTTON_EASY,easy);
        intent.putExtra(HomeActivity.EXTRA_IS_BUTTON_HARD,hard);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    // create the home button
    public void home(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    // Disable Return Button
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }

        return super.dispatchKeyEvent(event);
    }

    }
