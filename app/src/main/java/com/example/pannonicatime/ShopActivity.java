package com.example.pannonicatime;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pannonicatime.database.AppDatabase;
import com.example.pannonicatime.model.Proizvod;
import com.example.pannonicatime.model.Narudzba;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShopActivity extends AppCompatActivity {

    private Button btnMinusRedovna, btnPlusRedovna, btnMinusDjecija, btnPlusDjecija,
            btnMinusPovlastena, btnPlusPovlastena, btnMinusLezaljka, btnPlusLezaljka,
            btnMinusSuncobran, btnPlusSuncobran, btnPotvrdiKupovinu;
    private TextView tvKolicinaRedovna, tvKolicinaDjecija, tvKolicinaPovlastena,
            tvKolicinaLezaljka, tvKolicinaSuncobran, tvUkupnaCijena;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        db = AppDatabase.getInstance(this);
        inicijalizirajBazu();

        btnMinusRedovna = findViewById(R.id.btnMinusRedovna);
        btnPlusRedovna = findViewById(R.id.btnPlusRedovna);
        tvKolicinaRedovna = findViewById(R.id.tvKolicinaRedovna);
        btnMinusDjecija = findViewById(R.id.btnMinusDjecija);
        btnPlusDjecija = findViewById(R.id.btnPlusDjecija);
        tvKolicinaDjecija = findViewById(R.id.tvKolicinaDjecija);
        btnMinusPovlastena = findViewById(R.id.btnMinusPovlastena);
        btnPlusPovlastena = findViewById(R.id.btnPlusPovlastena);
        tvKolicinaPovlastena = findViewById(R.id.tvKolicinaPovlastena);
        btnMinusLezaljka = findViewById(R.id.btnMinusLezaljka);
        btnPlusLezaljka = findViewById(R.id.btnPlusLezaljka);
        tvKolicinaLezaljka = findViewById(R.id.tvKolicinaLezaljka);
        btnMinusSuncobran = findViewById(R.id.btnMinusSuncobran);
        btnPlusSuncobran = findViewById(R.id.btnPlusSuncobran);
        tvKolicinaSuncobran = findViewById(R.id.tvKolicinaSuncobran);
        tvUkupnaCijena = findViewById(R.id.tvUkupnaCijena);
        btnPotvrdiKupovinu = findViewById(R.id.btnPotvrdiKupovinu);

        postaviKlikListener("Redovna ulaznica", btnPlusRedovna, btnMinusRedovna, tvKolicinaRedovna);
        postaviKlikListener("Dječija ulaznica", btnPlusDjecija, btnMinusDjecija, tvKolicinaDjecija);
        postaviKlikListener("Povlaštena ulaznica", btnPlusPovlastena, btnMinusPovlastena, tvKolicinaPovlastena);
        postaviKlikListener("Ležaljka", btnPlusLezaljka, btnMinusLezaljka, tvKolicinaLezaljka);
        postaviKlikListener("Suncobran", btnPlusSuncobran, btnMinusSuncobran, tvKolicinaSuncobran);

        findViewById(R.id.cardPromocija1).setOnClickListener(v -> dodajPromociju("Porodični Vikend Paket"));
        findViewById(R.id.cardPromocija2).setOnClickListener(v -> dodajPromociju("Studentski Paket 3+1"));
        findViewById(R.id.cardPromocija3).setOnClickListener(v -> dodajPromociju("Penzionerski Mjesečni Paket"));

        btnPotvrdiKupovinu.setOnClickListener(v -> potvrdiKupovinu());
        ucitajStanjeIzBaze();
    }

    private void dodajPromociju(String naziv) {
        Proizvod p = db.proizvodDao().dajProizvodPoNazivu(naziv);
        if (p != null) {
            p.setKolicina(p.getKolicina() + 1);
            db.proizvodDao().azurirajProizvod(p);
            Toast.makeText(this, naziv + " dodan u korpu!", Toast.LENGTH_SHORT).show();
            osvjeziKorpu();
        }
    }

    private void potvrdiKupovinu() {
        String userEmail = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE).getString("email", "Gost");

        StringBuilder detalji = new StringBuilder();
        double ukupno = 0;
        boolean imaStavki = false;

        for (Proizvod p : db.proizvodDao().dajSveProizvode()) {
            if (p.getKolicina() > 0) {
                detalji.append(p.getNaziv()).append(" (").append(p.getKolicina()).append("), ");
                ukupno += (p.getCijena() * p.getKolicina());
                imaStavki = true;
            }
        }

        if (imaStavki) {
            String datum = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(new Date());

            Narudzba n = new Narudzba(userEmail, detalji.toString(), ukupno, datum);

            db.narudzbaDao().dodajNarudzbu(n);
            Toast.makeText(this, "Narudžba potvrđena za: " + userEmail, Toast.LENGTH_SHORT).show();
            resetujSveKolicine();
        } else {
            Toast.makeText(this, "Korpa je prazna!", Toast.LENGTH_SHORT).show();
        }
    }

    private void inicijalizirajBazu() {
        String[] nazivi = {
                "Redovna ulaznica", "Dječija ulaznica", "Povlaštena ulaznica", "Ležaljka", "Suncobran",
                "Porodični Vikend Paket", "Studentski Paket 3+1", "Penzionerski Mjesečni Paket"
        };
        double[] cijene = {8.0, 4.0, 5.0, 5.0, 3.0, 20.0, 15.0, 45.0};

        for (int i = 0; i < nazivi.length; i++) {
            if (db.proizvodDao().dajProizvodPoNazivu(nazivi[i]) == null) {
                db.proizvodDao().dodajProizvod(new Proizvod(nazivi[i], "Opis", cijene[i], ""));
            }
        }
    }

    private void postaviKlikListener(String naziv, Button plus, Button minus, TextView tv) {
        plus.setOnClickListener(v -> {
            Proizvod p = db.proizvodDao().dajProizvodPoNazivu(naziv);
            if (p != null) {
                p.setKolicina(p.getKolicina() + 1);
                db.proizvodDao().azurirajProizvod(p);
                tv.setText(String.valueOf(p.getKolicina()));
                osvjeziKorpu();
            }
        });
        minus.setOnClickListener(v -> {
            Proizvod p = db.proizvodDao().dajProizvodPoNazivu(naziv);
            if (p != null && p.getKolicina() > 0) {
                p.setKolicina(p.getKolicina() - 1);
                db.proizvodDao().azurirajProizvod(p);
                tv.setText(String.valueOf(p.getKolicina()));
                osvjeziKorpu();
            }
        });
    }

    private void ucitajStanjeIzBaze() {
        Proizvod p1 = db.proizvodDao().dajProizvodPoNazivu("Redovna ulaznica");
        if (p1 != null) tvKolicinaRedovna.setText(String.valueOf(p1.getKolicina()));
        Proizvod p2 = db.proizvodDao().dajProizvodPoNazivu("Dječija ulaznica");
        if (p2 != null) tvKolicinaDjecija.setText(String.valueOf(p2.getKolicina()));
        Proizvod p3 = db.proizvodDao().dajProizvodPoNazivu("Povlaštena ulaznica");
        if (p3 != null) tvKolicinaPovlastena.setText(String.valueOf(p3.getKolicina()));
        Proizvod p4 = db.proizvodDao().dajProizvodPoNazivu("Ležaljka");
        if (p4 != null) tvKolicinaLezaljka.setText(String.valueOf(p4.getKolicina()));
        Proizvod p5 = db.proizvodDao().dajProizvodPoNazivu("Suncobran");
        if (p5 != null) tvKolicinaSuncobran.setText(String.valueOf(p5.getKolicina()));
        osvjeziKorpu();
    }

    private void osvjeziKorpu() {
        double ukupno = 0;
        for (Proizvod p : db.proizvodDao().dajSveProizvode()) {
            ukupno += (p.getCijena() * p.getKolicina());
        }
        tvUkupnaCijena.setText((int)ukupno + " KM");
    }

    private void resetujSveKolicine() {
        for (Proizvod p : db.proizvodDao().dajSveProizvode()) {
            p.setKolicina(0);
            db.proizvodDao().azurirajProizvod(p);
        }
        ucitajStanjeIzBaze();
    }
}