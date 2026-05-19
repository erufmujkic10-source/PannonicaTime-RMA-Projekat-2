package com.example.pannonicatime;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShopActivity extends AppCompatActivity {

    private CardView cardRedovna, cardPovlastena, cardDjecija;
    private EditText etKolicina;
    private ScrollView glavniScrollView;
    private View layoutKartaRezultat;
    private TextView tvDetaljiRacuna;
    private ImageView ivQrKod;


    private int selektovanaKarta = 0;

    private final double CIJENA_REDOVNA = 6.00;
    private final double CIJENA_POVLAŠTENA = 4.00;
    private final double CIJENA_DJEČIJA = 3.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        cardRedovna = findViewById(R.id.cardRedovna);
        cardPovlastena = findViewById(R.id.cardPovlastena);
        cardDjecija = findViewById(R.id.cardDjecija);
        etKolicina = findViewById(R.id.etKolicina);
        glavniScrollView = findViewById(R.id.glavniScrollView);
        layoutKartaRezultat = findViewById(R.id.layoutKartaRezultat);
        tvDetaljiRacuna = findViewById(R.id.tvDetaljiRacuna);
        ivQrKod = findViewById(R.id.ivQrKod);


        osjeziSelektovaniIzgled();


        cardRedovna.setOnClickListener(v -> {
            selektovanaKarta = 0;
            osjeziSelektovaniIzgled();
        });

        cardPovlastena.setOnClickListener(v -> {
            selektovanaKarta = 1;
            osjeziSelektovaniIzgled();
        });

        cardDjecija.setOnClickListener(v -> {
            selektovanaKarta = 2;
            osjeziSelektovaniIzgled();
        });


        findViewById(R.id.btnKupi).setOnClickListener(v -> izvrsiKupovinu());
    }

    private void osjeziSelektovaniIzgled() {

        cardRedovna.setCardBackgroundColor(Color.WHITE);
        cardPovlastena.setCardBackgroundColor(Color.WHITE);
        cardDjecija.setCardBackgroundColor(Color.WHITE);


        if (selektovanaKarta == 0) {
            cardRedovna.setCardBackgroundColor(Color.parseColor("#E0F2FE"));
        } else if (selektovanaKarta == 1) {
            cardPovlastena.setCardBackgroundColor(Color.parseColor("#E0F2FE"));
        } else if (selektovanaKarta == 2) {
            cardDjecija.setCardBackgroundColor(Color.parseColor("#E0F2FE"));
        }
    }

    private void izvrsiKupovinu() {
        String kolicinaTxt = etKolicina.getText().toString().trim();
        if (kolicinaTxt.isEmpty()) {
            Toast.makeText(this, "Molimo unesite količinu karata!", Toast.LENGTH_SHORT).show();
            return;
        }

        int kolicina = Integer.parseInt(kolicinaTxt);
        if (kolicina <= 0) {
            Toast.makeText(this, "Količina mora biti veća od 0!", Toast.LENGTH_SHORT).show();
            return;
        }

        String nazivKarte = "";
        double cijenaPoKarti = 0;


        if (selektovanaKarta == 0) {
            nazivKarte = "Redovna ulaznica";
            cijenaPoKarti = CIJENA_REDOVNA;
        } else if (selektovanaKarta == 1) {
            nazivKarte = "Povlaštena ulaznica";
            cijenaPoKarti = CIJENA_POVLAŠTENA;
        } else if (selektovanaKarta == 2) {
            nazivKarte = "Dječija ulaznica";
            cijenaPoKarti = CIJENA_DJEČIJA;
        }

        double ukupnaCijena = kolicina * cijenaPoKarti;
        String datum = new SimpleDateFormat("dd.MM.yyyy. u HH:mm", Locale.getDefault()).format(new Date());


        String prikazSadrzaja = "🎫 " + nazivKarte + "\n\n" +
                "🔢 Količina: " + kolicina + " kom.\n" +
                "💰 Ukupno plaćeno: " + String.format(Locale.US, "%.2f", ukupnaCijena) + " KM\n\n" +
                "⏱ " + datum + " h";

        try {

            MultiFormatWriter writer = new MultiFormatWriter();
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap qrBitmap = encoder.createBitmap(writer.encode(prikazSadrzaja, BarcodeFormat.QR_CODE, 512, 512));


            ivQrKod.setImageBitmap(qrBitmap);
            tvDetaljiRacuna.setText(prikazSadrzaja);
            layoutKartaRezultat.setVisibility(View.VISIBLE);


            glavniScrollView.post(() -> glavniScrollView.fullScroll(View.FOCUS_DOWN));


            SharedPreferences pref = getSharedPreferences("PannonicaPrefs", Context.MODE_PRIVATE);
            String staraLista = pref.getString("KUPLJENE_KARTE", "");
            String novaLista = staraLista.isEmpty() ? prikazSadrzaja : staraLista + "##" + prikazSadrzaja;
            pref.edit().putString("KUPLJENE_KARTE", novaLista).apply();


            etKolicina.setText("");
            Toast.makeText(this, "Uspješno! Karta je sačuvana na Vašem Profilu.", Toast.LENGTH_LONG).show();

        } catch (Exception e) {
            Toast.makeText(this, "Greška pri obradi kupovine.", Toast.LENGTH_SHORT).show();
        }
    }
}