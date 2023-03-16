package com.example.myapplication.ui.splash;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY_MILLIS = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();

            }
        },SPLASH_DELAY_MILLIS);
//        new CountDownTimer(SPLASH_DELAY_MILLIS, SPLASH_DELAY_MILLIS) {
//            @Override
//            public void onTick(long millisUntilFinished) {}
//            @Override
//            public void onFinish() {
//
//            }
//        }.start();
    }
}