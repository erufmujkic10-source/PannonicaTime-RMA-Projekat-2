package com.example.pannonicatime;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends Activity {

    private Spinner spinnerJezik;
    private Switch switchObavijesti, switchNovosti, switchTamnaTema;
    private Button btnSpasiPostavke;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        spinnerJezik = findViewById(R.id.spinnerJezik);
        switchObavijesti = findViewById(R.id.switchObavijesti);
        switchNovosti = findViewById(R.id.switchNovosti);
        switchTamnaTema = findViewById(R.id.switchTamnaTema);
        btnSpasiPostavke = findViewById(R.id.btnSpasiPostavke);


        String[] jezici = {"Bosanski / Hrvatski / Srpski", "English", "Deutsch"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, jezici);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJezik.setAdapter(adapter);


        sharedPreferences = getSharedPreferences("PostavkeAplikacije", MODE_PRIVATE);
        int spaseniJezikIndeks = sharedPreferences.getInt("jezik_indeks", 0);
        boolean obavijestiAktivne = sharedPreferences.getBoolean("obavijesti", true);
        boolean novostiAktivne = sharedPreferences.getBoolean("novosti", false);
        boolean tamnaTemaAktivna = sharedPreferences.getBoolean("tamna_tema", false);


        spinnerJezik.setSelection(spaseniJezikIndeks);
        switchObavijesti.setChecked(obavijestiAktivne);
        switchNovosti.setChecked(novostiAktivne);
        switchTamnaTema.setChecked(tamnaTemaAktivna);


        btnSpasiPostavke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selektovaniJezikIndeks = spinnerJezik.getSelectedItemPosition();
                String izabraniJezik = spinnerJezik.getSelectedItem().toString();
                boolean obavijesti = switchObavijesti.isChecked();
                boolean novosti = switchNovosti.isChecked();
                boolean tamnaTema = switchTamnaTema.isChecked();


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("jezik_indeks", selektovaniJezikIndeks);
                editor.putString("jezik_naziv", izabraniJezik);
                editor.putBoolean("obavijesti", obavijesti);
                editor.putBoolean("novosti", novosti);
                editor.putBoolean("tamna_tema", tamnaTema);
                editor.commit(); // Siguran instantni upis na disk

                Toast.makeText(SettingsActivity.this, "Postavke su uspješno spremljene!", Toast.LENGTH_SHORT).show();
            }
        });


        LinearLayout menuHome = findViewById(R.id.menuHome);
        LinearLayout menuSearch = findViewById(R.id.menuSearch);
        LinearLayout menuProfile = findViewById(R.id.menuProfile);

        menuHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        menuSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, SearchActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });

        menuProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
            }
        });
    }
}