package com.example.pannonicatime;

import android.app.Activity;
import android.content.Intent;
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

public class LoginActivity extends Activity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvIdiNaRegistraciju;
    private AppDatabase baza;


    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvIdiNaRegistraciju = findViewById(R.id.tvIdiNaRegistraciju);


        baza = AppDatabase.getInstance(this);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString().trim();
                final String password = etPassword.getText().toString().trim();


                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Molimo popunite sva polja!", Toast.LENGTH_SHORT).show();
                    return;
                }


                btnLogin.setEnabled(false);


                executorService.execute(new Runnable() {
                    @Override
                    public void run() {

                        final User user = baza.userDao().login(email, password);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                btnLogin.setEnabled(true);

                                if (user != null) {

                                    SharedPreferences sharedPreferences = getSharedPreferences("KorisnickiPodaci", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("ime", user.getIme());
                                    editor.putString("email", user.getEmail());
                                    editor.putBoolean("isLoggedIn", true);
                                    editor.apply();

                                    Toast.makeText(LoginActivity.this, "Uspješna prijava! Dobrodošli " + user.getIme(), Toast.LENGTH_SHORT).show();


                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Pogrešan email ili lozinka!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            }
        });


        tvIdiNaRegistraciju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        executorService.shutdown();
    }
}