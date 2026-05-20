package com.example.pannonicatime;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ProfileActivity extends AppCompatActivity {

    private EditText etIme, etAdresa, etTelefon, etStaraLozinka, etNovaLozinka;
    private androidx.appcompat.widget.AppCompatButton btnSpasiPodatke, btnPromjeniLozinku;
    private LinearLayout kontejnerZaNarudzbe, layoutProfilQrPrikaz;
    private ImageView ivProfilQrKod;

    private SharedPreferences sharedPref;
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
        kontejnerZaNarudzbe = findViewById(R.id.kontejnerZaNarudzbe);
        layoutProfilQrPrikaz = findViewById(R.id.layoutProfilQrPrikaz);
        ivProfilQrKod = findViewById(R.id.ivProfilQrKod);

        sharedPref = getSharedPreferences("PannonicaPrefs", Context.MODE_PRIVATE);

        loginPref = getSharedPreferences("KorisnickiPodaci", Context.MODE_PRIVATE);

        String stvarniIme = loginPref.getString("ime", sharedPref.getString("KORISNIK_IME", "Korisnik"));

        String stvarnaAdresa = sharedPref.getString("KORISNIK_ADRESA", "Tuzla, BiH");
        String stvarniTelefon = sharedPref.getString("KORISNIK_TELEFON", "+387 61 000 000");

        etIme.setText(stvarniIme);
        etAdresa.setText(stvarnaAdresa);
        etTelefon.setText(stvarniTelefon);

        prikaziSveKupljeneKarte();

        btnSpasiPodatke.setOnClickListener(v -> {
            String novoIme = etIme.getText().toString().trim();
            String novaAdresa = etAdresa.getText().toString().trim();
            String noviTelefon = etTelefon.getText().toString().trim();

            if (novoIme.isEmpty() || novaAdresa.isEmpty() || noviTelefon.isEmpty()) {
                Toast.makeText(this, "Popunite sva polja!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("KORISNIK_IME", novoIme);
            editor.putString("KORISNIK_ADRESA", novaAdresa);
            editor.putString("KORISNIK_TELEFON", noviTelefon);
            editor.apply();

            SharedPreferences.Editor loginEditor = loginPref.edit();
            loginEditor.putString("ime", novoIme);
            loginEditor.apply();

            Toast.makeText(this, "Podaci ažurirani!", Toast.LENGTH_SHORT).show();
        });

        btnPromjeniLozinku.setOnClickListener(v -> {
            String stara = etStaraLozinka.getText().toString().trim();
            String nova = etNovaLozinka.getText().toString().trim();
            String lozinkaSistem = sharedPref.getString("KORISNIK_LOZINKA", "123456");

            if (stara.isEmpty() || nova.isEmpty()) {
                Toast.makeText(this, "Unesite lozinke!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!stara.equals(lozinkaSistem)) {
                Toast.makeText(this, "Trenutna lozinka netačna!", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("KORISNIK_LOZINKA", nova);
            editor.apply();

            etStaraLozinka.setText("");
            etNovaLozinka.setText("");
            Toast.makeText(this, "Lozinka uspješno izmijenjena!", Toast.LENGTH_SHORT).show();
        });
    }

    private void prikaziSveKupljeneKarte() {
        kontejnerZaNarudzbe.removeAllViews();
        String dugačkiNizKarata = sharedPref.getString("KUPLJENE_KARTE", "");

        if (dugačkiNizKarata.isEmpty()) {
            TextView tvPrazno = new TextView(this);
            tvPrazno.setText("Nemate kupljenih karata u istoriji.");
            tvPrazno.setTextColor(Color.parseColor("#64748B"));
            tvPrazno.setTextSize(14);
            kontejnerZaNarudzbe.addView(tvPrazno);
            return;
        }

        String[] sveKarte = dugačkiNizKarata.split("##");

        for (int i = sveKarte.length - 1; i >= 0; i--) {
            String podaciTrenutneKarte = sveKarte[i];

            CardView card = new CardView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 0, 0, 24);
            card.setLayoutParams(params);
            card.setRadius(32);
            card.setCardBackgroundColor(Color.WHITE);
            card.setClickable(true);
            card.setFocusable(true);

            LinearLayout unutarCard = new LinearLayout(this);
            unutarCard.setOrientation(LinearLayout.VERTICAL);
            unutarCard.setPadding(36, 32, 36, 32);

            TextView tvTekst = new TextView(this);
            tvTekst.setText("🎫 Narudžba #" + (i + 1) + "\n\n" + podaciTrenutneKarte);
            tvTekst.setTextColor(Color.parseColor("#1E293B"));
            tvTekst.setTextSize(14);
            tvTekst.setLineSpacing(1.2f, 1.2f);

            unutarCard.addView(tvTekst);
            card.addView(unutarCard);

            card.setOnClickListener(v -> {
                MultiFormatWriter writer = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = writer.encode(podaciTrenutneKarte, BarcodeFormat.QR_CODE, 500, 500);
                    BarcodeEncoder encoder = new BarcodeEncoder();
                    Bitmap bitmap = encoder.createBitmap(bitMatrix);

                    ivProfilQrKod.setImageBitmap(bitmap);
                    layoutProfilQrPrikaz.setVisibility(View.VISIBLE);

                    layoutProfilQrPrikaz.getParent().requestChildFocus(layoutProfilQrPrikaz, layoutProfilQrPrikaz);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            });

            kontejnerZaNarudzbe.addView(card);
        }
    }
}