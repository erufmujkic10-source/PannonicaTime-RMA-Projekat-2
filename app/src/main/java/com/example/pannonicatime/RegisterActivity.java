package com.example.pannonicatime;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pannonicatime.database.AppDatabase;
import com.example.pannonicatime.model.User;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegisterActivity extends Activity {

    private EditText etRegIme, etRegEmail, etRegPassword;
    private Button btnRegister;
    private TextView tvNazadNaLogin;
    private AppDatabase baza;


    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etRegIme = findViewById(R.id.etRegIme);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvNazadNaLogin = findViewById(R.id.tvNazadNaLogin);


        baza = AppDatabase.getInstance(this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ime = etRegIme.getText().toString().trim();
                final String email = etRegEmail.getText().toString().trim();
                final String password = etRegPassword.getText().toString().trim();


                if (ime.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Molimo popunite sva polja!", Toast.LENGTH_SHORT).show();
                    return;
                }


                btnRegister.setEnabled(false);


                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        final User postojeciKorisnik = baza.userDao().provjeriEmail(email);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (postojeciKorisnik != null) {

                                    btnRegister.setEnabled(true);
                                    Toast.makeText(RegisterActivity.this, "Korisnik sa ovim emailom već postoji!", Toast.LENGTH_SHORT).show();
                                } else {

                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            User noviKorisnik = new User(ime, email, password);
                                            baza.userDao().registrujKorisnika(noviKorisnik);


                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    SharedPreferences sharedPreferences = getSharedPreferences("KorisnickiPodaci", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("ime", ime);
                                                    editor.putString("email", email);
                                                    editor.apply();

                                                    Toast.makeText(RegisterActivity.this, "Registracija uspješna! Prijavite se.", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            });
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
            }
        });


        tvNazadNaLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        executorService.shutdown();
    }
}