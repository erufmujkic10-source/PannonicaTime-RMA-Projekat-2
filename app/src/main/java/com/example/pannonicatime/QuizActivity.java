package com.example.pannonicatime;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {

    private TextView tvPitanje, tvOpcija1, tvOpcija2, tvOpcija3;
    private CardView cardOpcija1, cardOpcija2, cardOpcija3;

    private List<Pitanje> listaPitanja;
    private int trenutniIndeksPitanja = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvPitanje = findViewById(R.id.tvPitanje);

        tvOpcija1 = findViewById(R.id.btnOpcija1);
        tvOpcija2 = findViewById(R.id.btnOpcija2);
        tvOpcija3 = findViewById(R.id.btnOpcija3);

        cardOpcija1 = findViewById(R.id.cardOpcija1);
        cardOpcija2 = findViewById(R.id.cardOpcija2);
        cardOpcija3 = findViewById(R.id.cardOpcija3);

        ucitajPitanja();
        prikaziPitanje();
    }

    private void ucitajPitanja() {
        listaPitanja = new ArrayList<>();

        listaPitanja.add(new Pitanje(
                "Koja je glavna karakteristika vode u Panonskim jezerima?",
                new String[]{"Voda je slatka i pitka", "Voda je slana i ljekovita", "Voda je termalna i sumporna"},
                1
        ));

        listaPitanja.add(new Pitanje(
                "Šta predstavlja arheološki park smješten unutar kompleksa Panonskih jezera?",
                new String[]{"Neolitsko sojenčko naselje", "Rimski dvorac iz antike", "Srednjovjekovnu tvrđavu"},
                0
        ));

        listaPitanja.add(new Pitanje(
                "Koliki je otprilike salinitet vode u Panonskim jezerima?",
                new String[]{"Od 5 do 10 grama soli po litru", "Od 30 do 40 grama soli po litru", "Oko 100 grama soli po litru"},
                1
        ));

        listaPitanja.add(new Pitanje(
                "Čemu prvenstveno služe popularni 'Slani slapovi' unutar kompleksa?",
                new String[]{"Kao hidromasažni bazen za plivanje", "Kao inhalatorni zdravstveni centar na otvorenom", "Kao sistem za navodnjavanje parka"},
                1
        ));

        listaPitanja.add(new Pitanje(
                "Po čemu je grad Tuzla, u kojem se nalaze Panonska jezera, historijski najviše poznat?",
                new String[]{"Po rudnicima uglja i bogatim nalazištima soli", "Po proizvodnji čelika i automobila", "Po prostranim vinogradima i vinu"},
                0
        ));

        Collections.shuffle(listaPitanja);
    }

    private void prikaziPitanje() {
        if (trenutniIndeksPitanja < listaPitanja.size()) {
            Pitanje p = listaPitanja.get(trenutniIndeksPitanja);

            tvPitanje.setText(p.getTekstPitanja());
            String[] opcije = p.getOpcije();

            tvOpcija1.setText(opcije[0]);
            tvOpcija2.setText(opcije[1]);
            tvOpcija3.setText(opcije[2]);

            postaviKlikNaKarticu(cardOpcija1, 0, p);
            postaviKlikNaKarticu(cardOpcija2, 1, p);
            postaviKlikNaKarticu(cardOpcija3, 2, p);

        } else {
            Toast.makeText(this, "Čestitamo! Uspješno ste završili kviz o Panonici! 🎯", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void postaviKlikNaKarticu(CardView kartica, int odabraniIndeks, Pitanje pitanje) {
        kartica.setOnClickListener(v -> {
            if (odabraniIndeks == pitanje.getTacanIndeks()) {
                Toast.makeText(QuizActivity.this, "Tačno! 🎯 Idemo na sljedeće pitanje.", Toast.LENGTH_SHORT).show();
                trenutniIndeksPitanja++;
                prikaziPitanje();
            } else {
                Toast.makeText(QuizActivity.this, "Pogrešno. Pokušajte ponovo! ❌", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class Pitanje {
        private String tekstPitanja;
        private String[] opcije;
        private int tacanIndeks;

        public Pitanje(String tekstPitanja, String[] opcije, int tacanIndeks) {
            this.tekstPitanja = tekstPitanja;
            this.opcije = opcije;
            this.tacanIndeks = tacanIndeks;
        }

        public String getTekstPitanja() { return tekstPitanja; }
        public String[] getOpcije() { return opcije; }
        public int getTacanIndeks() { return tacanIndeks; }
    }
}