package com.example.pannonicatime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.example.myapplication.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ProgressBar prikazTalasa = findViewById(R.id.prikazTalasa);


        Animation animacija = AnimationUtils.loadAnimation(this, R.anim.talas_animacija);


        if (prikazTalasa != null && animacija != null) {
            prikazTalasa.startAnimation(animacija);
        }


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);


                finish();
            }
        }, 3000);
    }
}