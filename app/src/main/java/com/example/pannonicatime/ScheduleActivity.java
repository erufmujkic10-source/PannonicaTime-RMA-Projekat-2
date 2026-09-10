package com.example.pannonicatime;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView rvRaspored;
    private List<DogadjajStavka> listaDogadjaja;
    private DogadjajAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        rvRaspored = findViewById(R.id.rvRaspored);
        rvRaspored.setLayoutManager(new LinearLayoutManager(this));

        ucitajSedmicniProgram();

        adapter = new DogadjajAdapter(listaDogadjaja, this);
        rvRaspored.setAdapter(adapter);
    }

    private void ucitajSedmicniProgram() {
        listaDogadjaja = new ArrayList<>();

        listaDogadjaja.add(new DogadjajStavka("PONEDJELJAK", "🌊 Škola plivanja", "10:00 - 11:30", "Besplatna škola plivanja za najmlađe na Drugom jezeru.", R.drawable.swimming));
        listaDogadjaja.add(new DogadjajStavka("UTORAK", "🏐 Turnir u odbojci na pijesku", "16:00 - 20:00", "Prijave ekipa na info pultu kompleksa.", R.drawable.volleyball));
        listaDogadjaja.add(new DogadjajStavka("SRIJEDA", "🧘 Jutarnja joga na plaži", "08:30 - 09:30", "Opustite se uz lagane vježbe pored Slanih slapova.", R.drawable.yoga));
        listaDogadjaja.add(new DogadjajStavka("ČETVRTAK", "🎵 Akustična večer", "19:00 - 21:00", "Muzika uživo kod ugostiteljskih objekata.", R.drawable.concert));
        listaDogadjaja.add(new DogadjajStavka("PETAK", "🏆 Porodični sportski dan", "14:00 - 18:00", "Igre bez granica za cijelu porodicu.", R.drawable.familysport));
        listaDogadjaja.add(new DogadjajStavka("SUBOTA", "🏊 Plivački maraton", "11:00 - 13:00", "Rekreativno takmičenje na Prvom jezeru.", R.drawable.swimmingmarathon));
        listaDogadjaja.add(new DogadjajStavka("NEDJELJA", "🎨 Dječja radionica", "10:00 - 12:00", "Kreativna zabava sa glinom i crtanje pored neolitskog naselja.", R.drawable.people));
    }
}