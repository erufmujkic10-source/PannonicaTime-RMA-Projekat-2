package com.example.pannonicatime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.example.pannonicatime.database.AppDatabase;
import com.example.pannonicatime.model.Narudzba;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private EditText etIme, etAdresa, etTelefon, etStaraLozinka, etNovaLozinka;
    private AppCompatButton btnSpasiPodatke, btnPromjeniLozinku, btnLogout;
    private LinearLayout kontejnerZaNarudzbe, layoutProfilQrPrikaz;
    private ImageView ivProfilQrKod;
    private SharedPreferences loginPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etIme = findViewById(R.id.etProfilIme);
        etAdresa = findViewById(R.id.etProfilAdresa);
        etTelefon = findViewById(R.id.etProfilTelefon);
        etStaraLozinka = findViewById(R.id.etStaraLozinka);
        etNovaLozinka = findViewById(R.id.etNovaLozinka);
        btnSpasiPodatke = findViewById(R.id.btnSpasiPodatke);
        btnPromjeniLozinku = findViewById(R.id.btnPromjeniLozinku);
        btnLogout = findViewById(R.id.btnLogout);
        kontejnerZaNarudzbe = findViewById(R.id.kontejnerZaNarudzbe);
        layoutProfilQrPrikaz = findViewById(R.id.layoutProfilQrPrikaz);
        ivProfilQrKod = findViewById(R.id.ivProfilQrKod);

        String trenutniEmail = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).getString("email", "gost");
        String imeFajla = "podaci_" + trenutniEmail;
        loginPref = getSharedPreferences(imeFajla, Context.MODE_PRIVATE);

        etIme.setText(loginPref.getString("ime", "Korisnik"));
        etAdresa.setText(loginPref.getString("grad", "Tuzla"));
        etTelefon.setText(loginPref.getString("telefon", "+387 61 000 000"));

        prikaziSveKupljeneKarte();

        layoutProfilQrPrikaz.setOnClickListener(v -> layoutProfilQrPrikaz.setVisibility(View.GONE));

        btnSpasiPodatke.setOnClickListener(v -> {
            String novoIme = etIme.getText().toString().trim();
            String novaAdresa = etAdresa.getText().toString().trim();
            String noviTelefon = etTelefon.getText().toString().trim();

            loginPref.edit()
                    .putString("ime", novoIme)
                    .putString("grad", novaAdresa)
                    .putString("telefon", noviTelefon)
                    .apply();

            Toast.makeText(this, "Podaci sačuvani!", Toast.LENGTH_SHORT).show();
        });

        btnPromjeniLozinku.setOnClickListener(v -> {
            String stara = etStaraLozinka.getText().toString().trim();
            String nova = etNovaLozinka.getText().toString().trim();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null && user.getEmail() != null) {
                AuthCredential cred = EmailAuthProvider.getCredential(user.getEmail(), stara);
                user.reauthenticate(cred).addOnSuccessListener(a ->
                        user.updatePassword(nova).addOnSuccessListener(b ->
                                Toast.makeText(this, "Lozinka izmijenjena!", Toast.LENGTH_SHORT).show())
                ).addOnFailureListener(e -> Toast.makeText(this, "Greška: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        });

        btnLogout.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void prikaziSveKupljeneKarte() {
        kontejnerZaNarudzbe.removeAllViews();
        String trenutniEmail = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).getString("email", "");
        List<Narudzba> listaNarudzbi = AppDatabase.getInstance(this).narudzbaDao().dajNarudzbeZaKorisnika(trenutniEmail);

        if (listaNarudzbi == null || listaNarudzbi.isEmpty()) return;

        for (Narudzba n : listaNarudzbi) {
            CardView card = new CardView(this);
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
            p.setMargins(0, 0, 0, 24);
            card.setLayoutParams(p);
            card.setRadius(16);
            card.setCardElevation(8);
            card.setCardBackgroundColor(getTemaBoja(com.google.android.material.R.attr.colorSurface));

            TextView tv = new TextView(this);
            tv.setPadding(40, 40, 40, 40);
            tv.setTextColor(getTemaBoja(android.R.attr.textColorPrimary));
            tv.setText("🎫 Narudžba #" + n.id + "\nDatum: " + n.datum + "\nStavke: " + n.detalji + "\nUkupno: " + (int)n.ukupnaCijena + " KM");

            card.addView(tv);

            card.setOnClickListener(v -> {
                try {
                    String podaciZaQr = "ID:" + n.id + " | Email:" + n.korisnikEmail + " | " + n.detalji;
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.encodeBitmap(podaciZaQr, BarcodeFormat.QR_CODE, 500, 500);

                    ivProfilQrKod.setImageBitmap(bitmap);
                    layoutProfilQrPrikaz.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            kontejnerZaNarudzbe.addView(card);
        }
    }

    private int getTemaBoja(int attr) {
        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(attr, typedValue, true);
        return typedValue.data;
    }
}