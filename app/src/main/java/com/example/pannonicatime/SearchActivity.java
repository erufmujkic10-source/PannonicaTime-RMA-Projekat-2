package com.example.pannonicatime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SearchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        LinearLayout menuHome = findViewById(R.id.menuHome);
        LinearLayout menuProfile = findViewById(R.id.menuProfile);

        menuHome.setOnClickListener(v -> {
            startActivity(new Intent(SearchActivity.this, MainActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });

        menuProfile.setOnClickListener(v -> {
            startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
            overridePendingTransition(0, 0);
            finish();
        });
    }
}