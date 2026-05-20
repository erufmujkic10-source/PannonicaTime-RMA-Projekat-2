package com.example.pannonicatime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShopActivity extends AppCompatActivity {

    private Button btnMinusRedovna, btnPlusRedovna;
    private TextView tvKolicinaRedovna;
    private int kolRedovna = 0;
    private final int CIJENA_REDOVNA = 8;

    private Button btnMinusDjecija, btnPlusDjecija;
    private TextView tvKolicinaDjecija;
    private int kolDjecija = 0;
    private final int CIJENA_DJECIJA = 4;

    private Button btnMinusPovlastena, btnPlusPovlastena;
    private TextView tvKolicinaPovlastena;
    private int kolPovlastena = 0;
    private final int CIJENA_POVLASTENA = 5;

    private TextView tvUkupnaCijena;
    private Button btnPotvrdiKupovinu;

    private CardView cardPromocija1, cardPromocija2, cardPromocija3;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        sharedPref = getSharedPreferences("PannonicaPrefs", Context.MODE_PRIVATE);

        btnMinusRedovna = findViewById(R.id.btnMinusRedovna);
        btnPlusRedovna = findViewById(R.id.btnPlusRedovna);
        tvKolicinaRedovna = findViewById(R.id.tvKolicinaRedovna);

        btnMinusDjecija = findViewById(R.id.btnMinusDjecija);
        btnPlusDjecija = findViewById(R.id.btnPlusDjecija);
        tvKolicinaDjecija = findViewById(R.id.tvKolicinaDjecija);

        btnMinusPovlastena = findViewById(R.id.btnMinusPovlastena);
        btnPlusPovlastena = findViewById(R.id.btnPlusPovlastena);
        tvKolicinaPovlastena = findViewById(R.id.tvKolicinaPovlastena);

        tvUkupnaCijena = findViewById(R.id.tvUkupnaCijena);
        btnPotvrdiKupovinu = findViewById(R.id.btnPotvrdiKupovinu);

        cardPromocija1 = findViewById(R.id.cardPromocija1);
        cardPromocija2 = findViewById(R.id.cardPromocija2);
        cardPromocija3 = findViewById(R.id.cardPromocija3);

        btnPlusRedovna.setOnClickListener(v -> {
            kolRedovna++;
            tvKolicinaRedovna.setText(String.valueOf(kolRedovna));
            osvjeziKorpu();
        });
        btnMinusRedovna.setOnClickListener(v -> {
            if (kolRedovna > 0) {
                kolRedovna--;
                tvKolicinaRedovna.setText(String.valueOf(kolRedovna));
                osvjeziKorpu();
            }
        });

        btnPlusDjecija.setOnClickListener(v -> {
            kolDjecija++;
            tvKolicinaDjecija.setText(String.valueOf(kolDjecija));
            osvjeziKorpu();
        });
        btnMinusDjecija.setOnClickListener(v -> {
            if (kolDjecija > 0) {
                kolDjecija--;
                tvKolicinaDjecija.setText(String.valueOf(kolDjecija));
                osvjeziKorpu();
            }
        });

        btnPlusPovlastena.setOnClickListener(v -> {
            kolPovlastena++;
            tvKolicinaPovlastena.setText(String.valueOf(kolPovlastena));
            osvjeziKorpu();
        });
        btnMinusPovlastena.setOnClickListener(v -> {
            if (kolPovlastena > 0) {
                kolPovlastena--;
                tvKolicinaPovlastena.setText(String.valueOf(kolPovlastena));
                osvjeziKorpu();
            }
        });

        btnPotvrdiKupovinu.setOnClickListener(v -> {
            int ukupnoKarata = kolRedovna + kolDjecija + kolPovlastena;

            if (ukupnoKarata == 0) {
                Toast.makeText(this, "Izaberite barem jednu kartu prije kupovine!", Toast.LENGTH_SHORT).show();
                return;
            }

            int konacnaCijena = (kolRedovna * CIJENA_REDOVNA) + (kolDjecija * CIJENA_DJECIJA) + (kolPovlastena * CIJENA_POVLASTENA);
            String trenutniDatum = new SimpleDateFormat("dd.MM.yyyy u HH:mm", Locale.getDefault()).format(new Date());

            StringBuilder racun = new StringBuilder();
            racun.append("Kupljeno: ").append(trenutniDatum).append("\n\n");
            if (kolRedovna > 0) racun.append("• Redovna karta x").append(kolRedovna).append(" (").append(kolRedovna * CIJENA_REDOVNA).append(" KM)\n");
            if (kolDjecija > 0) racun.append("• Dječija karta x").append(kolDjecija).append(" (").append(kolDjecija * CIJENA_DJECIJA).append(" KM)\n");
            if (kolPovlastena > 0) racun.append("• Povlaštena karta x").append(kolPovlastena).append(" (").append(kolPovlastena * CIJENA_POVLASTENA).append(" KM)\n");
            racun.append("\n---------------------------\n");
            racun.append("Ukupan iznos: ").append(konacnaCijena).append(" KM");

            spasiKartuUBazu(racun.toString());
            ocistiSveBrojace();
        });

        if (cardPromocija1 != null) {
            cardPromocija1.setOnClickListener(v -> prikaziDetaljePromocije(
                    "Porodični Vikend Paket",
                    "20.00 KM",
                    "Uključuje cjelodnevni ulaz za dvije odrasle osobe i dvoje djece (do 12 godina) tokom subote ili nedjelje. Idealno za porodični odmor na slanim jezerima!"
            ));
        }

        if (cardPromocija2 != null) {
            cardPromocija2.setOnClickListener(v -> prikaziDetaljePromocije(
                    "Studentski Paket 3+1",
                    "15.00 KM",
                    "Akcija za studente! Kupite 3 povlaštene studentske ulaznice odjednom, a četvrtu ulaznicu dobijate potpuno besplatno. Potrebno je pokazati indeks na ulazu."
            ));
        }

        if (cardPromocija3 != null) {
            cardPromocija3.setOnClickListener(v -> prikaziDetaljePromocije(
                    "Penzionerski Mjesečni Paket",
                    "45.00 KM",
                    "Omogućava neograničen ulaz u kompleks Panonike tokom 30 dana od dana kupovine. Ponuda važi isključivo za penzionere uz pokaz čekova."
            ));
        }

        osvjeziKorpu();
    }

    private void prikaziDetaljePromocije(final String naslov, final String cijena, String opis) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ShopActivity.this);
        View bottomSheetView = getLayoutInflater().inflate(R.layout.layout_promocija_details, null);
        bottomSheetDialog.setContentView(bottomSheetView);

        TextView tvNaslov = bottomSheetView.findViewById(R.id.tvDetaljiNaslov);
        TextView tvCijena = bottomSheetView.findViewById(R.id.tvDetaljiCijena);
        TextView tvOpis = bottomSheetView.findViewById(R.id.tvDetaljiOpis);
        Button btnKupi = bottomSheetView.findViewById(R.id.btnKupiPromociju);

        tvNaslov.setText(naslov);
        tvCijena.setText("Cijena: " + cijena);
        tvOpis.setText(opis);

        btnKupi.setOnClickListener(v -> {
            String trenutniDatum = new SimpleDateFormat("dd.MM.yyyy u HH:mm", Locale.getDefault()).format(new Date());

            StringBuilder racunPromocije = new StringBuilder();
            racunPromocije.append("Akcija: ").append(trenutniDatum).append("\n\n");
            racunPromocije.append("• ").append(naslov).append("\n");
            racunPromocije.append("Status: Aktivna promocija\n");
            racunPromocije.append("\n---------------------------\n");
            racunPromocije.append("Plaćeno: ").append(cijena);

            spasiKartuUBazu(racunPromocije.toString());

            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.show();
    }

    private void spasiKartuUBazu(String tekstRacuna) {
        String prethodneNarudzbe = sharedPref.getString("KUPLJENE_KARTE", "");
        String azuriranaIstorija;

        if (prethodneNarudzbe.isEmpty()) {
            azuriranaIstorija = tekstRacuna;
        } else {
            azuriranaIstorija = prethodneNarudzbe + "##" + tekstRacuna;
        }

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("KUPLJENE_KARTE", azuriranaIstorija);
        editor.apply();

        Toast.makeText(this, "Uspješna kupovina! Dodano na profil.", Toast.LENGTH_LONG).show();
    }

    private void osvjeziKorpu() {
        int trenutniIznos = (kolRedovna * CIJENA_REDOVNA) + (kolDjecija * CIJENA_DJECIJA) + (kolPovlastena * CIJENA_POVLASTENA);
        tvUkupnaCijena.setText(trenutniIznos + " KM");
    }

    private void ocistiSveBrojace() {
        kolRedovna = 0;
        kolDjecija = 0;
        kolPovlastena = 0;
        tvKolicinaRedovna.setText("0");
        tvKolicinaDjecija.setText("0");
        tvKolicinaPovlastena.setText("0");
        osvjeziKorpu();
    }
}