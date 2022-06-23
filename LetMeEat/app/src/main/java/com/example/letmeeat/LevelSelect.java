package com.example.letmeeat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
/*
this class is the activity for the level select menu
 */
public class LevelSelect extends AppCompatActivity implements View.OnClickListener {
    private Button mPlayEasy, mBack, mPlayHard;
    boolean isButtonEasy = true;
    boolean isButtonHard = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mPlayEasy = findViewById(R.id.easy);

        mPlayHard = findViewById(R.id.hard);
        mBack = findViewById(R.id.back);


        mPlayEasy.setOnClickListener(this);

        mPlayHard.setOnClickListener(this);

        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.easy:
                Intent easy = new Intent(this, HomeActivity.class);
                // this pass the value of the variable to other class
                easy.putExtra(HomeActivity.EXTRA_IS_BUTTON_EASY,isButtonEasy);
                startActivity(easy);
                finish();
                break;
            case R.id.hard:
                Intent hard = new Intent(this, HomeActivity.class);
                // this pass the value of the variable to other class
                hard.putExtra(HomeActivity.EXTRA_IS_BUTTON_HARD,isButtonHard);
                startActivity(hard);
                finish();
                break;
            case R.id.back:
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
    }
}
