package com.example.pannonicatime;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.firebase.FirebaseApp;

public class PannonicaModePamcenje extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);

        SharedPreferences sharedPreferences = getSharedPreferences("PostavkePrefs", Context.MODE_PRIVATE);
        boolean jeTamnaTema = sharedPreferences.getBoolean("TamnaTema", false);

        if (jeTamnaTema) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}