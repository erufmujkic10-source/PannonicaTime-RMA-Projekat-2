package com.example.pannonicatime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class SettingsActivity extends AppCompatActivity {

    private SwitchCompat switchTamnaTema, switchObavijesti;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("PostavkePrefs", Context.MODE_PRIVATE);
        boolean jeTamnaTema = sharedPreferences.getBoolean("TamnaTema", false);

        switchTamnaTema = findViewById(R.id.switchTamnaTema);
        switchObavijesti = findViewById(R.id.switchObavijesti);
        LinearLayout menuHome = findViewById(R.id.menuHome);

        if (switchTamnaTema != null) {
            switchTamnaTema.setChecked(jeTamnaTema);
            switchTamnaTema.setOnCheckedChangeListener((buttonView, isChecked) -> {
                sharedPreferences.edit().putBoolean("TamnaTema", isChecked).apply();
                AppCompatDelegate.setDefaultNightMode(isChecked ?
                        AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            });
        }

        if (switchObavijesti != null) {
            switchObavijesti.setChecked(sharedPreferences.getBoolean("Obavijesti", true));
            switchObavijesti.setOnCheckedChangeListener((buttonView, isChecked) -> {
                sharedPreferences.edit().putBoolean("Obavijesti", isChecked).apply();
            });
        }

        if (menuHome != null) {
            menuHome.setOnClickListener(v -> {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            });
        }
    }
}