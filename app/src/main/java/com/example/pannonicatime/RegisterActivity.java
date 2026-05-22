package com.example.pannonicatime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pannonicatime.database.AppDatabase;
import com.example.pannonicatime.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegIme, etRegEmail, etRegPassword;
    private Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        etRegIme = findViewById(R.id.etRegIme);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(v -> {
            String ime = etRegIme.getText().toString().trim();
            String email = etRegEmail.getText().toString().trim();
            String password = etRegPassword.getText().toString().trim();

            if (ime.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Popuni sva polja!", Toast.LENGTH_SHORT).show();
            } else {
                registrujKorisnika(ime, email, password);
            }
        });
    }

    private void registrujKorisnika(String ime, String email, String password) {
        //za firebase koristit
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();

                        Map<String, Object> userMap = new HashMap<>();
                        userMap.put("ime", ime);
                        userMap.put("email", email);

                        db.collection("korisnici").document(userId).set(userMap)
                                .addOnSuccessListener(aVoid -> {

                                    new Thread(() -> {
                                        User noviUser = new User(userId, ime, email);
                                        AppDatabase.getInstance(getApplicationContext()).userDao().insertUser(noviUser);

                                        runOnUiThread(() -> {
                                            Toast.makeText(this, "Registracija uspješna!", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                            finish();
                                        });
                                    }).start();

                                })
                                .addOnFailureListener(e -> Toast.makeText(this, "Greška baze: " + e.getMessage(), Toast.LENGTH_LONG).show());
                    } else {
                        Toast.makeText(this, "Greška: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}