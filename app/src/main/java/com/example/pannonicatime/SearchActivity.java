package com.example.pannonicatime;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private List<SearchStavka> kompletnaLista;
    private List<SearchStavka> filtriranaLista;
    private PretragaAdapter adapter;
    private EditText etPretraga;
    private RecyclerView rvPretraga;

    private LinearLayout menuHome;
    private LinearLayout menuProfile;
    private LinearLayout menuSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etPretraga = findViewById(R.id.etPretraga);
        rvPretraga = findViewById(R.id.rvPretraga);

        menuHome = findViewById(R.id.menuHome);
        menuProfile = findViewById(R.id.menuProfile);
        menuSettings = findViewById(R.id.menuSettings);

        ucitajSvePonudePanonike();

        filtriranaLista = new ArrayList<>(kompletnaLista);
        adapter = new PretragaAdapter(filtriranaLista, this);

        if (rvPretraga != null) {
            rvPretraga.setLayoutManager(new LinearLayoutManager(this));
            rvPretraga.setAdapter(adapter);
        }

        if (etPretraga != null) {
            etPretraga.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    izvrsiFiltriranje(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });
        }

        if (menuHome != null) {
            menuHome.setOnClickListener(v -> {
                startActivity(new Intent(SearchActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
            });
        }

        if (menuProfile != null) {
            menuProfile.setOnClickListener(v -> {
                startActivity(new Intent(SearchActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
            });
        }

        if (menuSettings != null) {
            menuSettings.setOnClickListener(v -> {
                startActivity(new Intent(SearchActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
                finish();
            });
        }
    }

    private void ucitajSvePonudePanonike() {
        kompletnaLista = new ArrayList<>();

        kompletnaLista.add(new SearchStavka("🏝️ Prvo Panonsko jezero", "Prvo izgrađeno jezero sa ljekovitom slanom vodom i velikom plažom.", "atrakcija", 0.00, R.drawable.prvo_jezero));
        kompletnaLista.add(new SearchStavka("🏊 Drugo Panonsko jezero", "Prelijepo vještačko jezero idealno za plivače i odmor.", "atrakcija", 0.00, R.drawable.drugo_jezero));
        kompletnaLista.add(new SearchStavka("🌊 Treće Panonsko jezero", "Najmodernije jezero u kompleksu sa vodenim toboganima.", "atrakcija", 0.00, R.drawable.trece_jezero));
        kompletnaLista.add(new SearchStavka("⛲ Slani slapovi", "Jedinstveni otvoreni inhalatorni zdravstveni centar na pet stepenica.", "atrakcija", 0.00, R.drawable.slapovi));
        kompletnaLista.add(new SearchStavka("🛖 Arheološki park neolit", "Replika sojeničkog neolitskog naselja iz davne istorije našeg grada.", "atrakcija", 0.00, R.drawable.sojenice));

        kompletnaLista.add(new SearchStavka("🎟️ Redovna ulaznica", "Dnevna ulaznica za odrasle osobe.", "karta", 6.00, R.drawable.ticket));
        kompletnaLista.add(new SearchStavka("🎓 Studentska ulaznica", "Dnevna ulaznica uz predočenje važećeg indeksa.", "karta", 4.50, R.drawable.student));
        kompletnaLista.add(new SearchStavka("🎖️ Povlaštena ulaznica", "Dnevna ulaznica za penzionere i invalide.", "karta", 4.00, R.drawable.privileged));
        kompletnaLista.add(new SearchStavka("🧒 Dječija ulaznica", "Dnevna ulaznica za djecu starosne dobi do 7 godina.", "karta", 3.00, R.drawable.kid));

        kompletnaLazy:
        kompletnaLista.add(new SearchStavka("🏖️ Platnena ležaljka", "Udobna i lagana ležaljka za cjelodnevno sunčanje.", "mobilijar", 5.00, R.drawable.chair));
        kompletnaLista.add(new SearchStavka("⛱️ Suncobran", "Veliki stabilni suncobran za bezbjedan hlad.", "mobilijar", 4.00, R.drawable.suncobran));
        kompletnaLista.add(new SearchStavka("☕ Stol", "Mali praktični sto za odlaganje osveženja.", "mobilijar", 2.00, R.drawable.table));

        kompletnaLista.add(new SearchStavka("🎁 Solo Paket", "Kombinacija: 1x redovna karta, 1x ležaljka i 1x suncobran.", "promo", 13.00, R.drawable.discount));
        kompletnaLista.add(new SearchStavka("✨ Premium Paket (Za dvoje)", "Kombinacija: 2x ležaljke, 1x stočić i 1x veliki suncobran.", "promo", 12.00, R.drawable.premium));
        kompletnaLista.add(new SearchStavka("👨‍👩‍👧‍👦 Porodični Paket", "Kombinacija: 2x redovne, 2x dječije karte + suncobran gratis.", "promo", 15.00, R.drawable.family));
    }

    private String ukloniKvacice(String tekst) {
        if (tekst == null) return "";
        return tekst.toLowerCase()
                .replace("ž", "z")
                .replace("š", "s")
                .replace("č", "c")
                .replace("ć", "c")
                .replace("đ", "d");
    }

    private void izvrsiFiltriranje(String tekst) {
        filtriranaLista.clear();
        if (tekst.isEmpty()) {
            filtriranaLista.addAll(kompletnaLista);
        } else {
            String trazeniTekst = ukloniKvacice(tekst.trim());

            boolean traziSveKarte = trazeniTekst.equals("karta") || trazeniTekst.equals("karte");

            for (SearchStavka stavka : kompletnaLista) {
                String naslovNormalizovan = ukloniKvacice(stavka.getNaslov());
                String opisNormalizovan = ukloniKvacice(stavka.getOpis());

                if (naslovNormalizovan.contains(trazeniTekst) ||
                        opisNormalizovan.contains(trazeniTekst) ||
                        (traziSveKarte && (stavka.getTip().equalsIgnoreCase("karta") || naslovNormalizovan.contains("ulaznica")))) {
                    filtriranaLista.add(stavka);
                }
            }
        }
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}