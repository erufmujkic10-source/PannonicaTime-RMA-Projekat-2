package com.example.pannonicatime;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    zakaziRandomPodsjetnik();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        provjeriIliZatrazitiDozvoluZaNotifikacije();

        Button btnIdiNaShop = findViewById(R.id.btnIdiNaShop);
        Button btnOtvoriKviz = findViewById(R.id.btnOtvoriKviz);

        CardView cardPocetnaPromoMamac = findViewById(R.id.cardPocetnaPromoMamac);
        CardView cardZanimljivosti = findViewById(R.id.cardZanimljivosti);
        TextView tvZanimljivostTekst = findViewById(R.id.tvZanimljivostTekst);

        CardView cardJezera = findViewById(R.id.cardJezera);
        CardView cardSlapovi = findViewById(R.id.cardSlapovi);
        CardView cardSojenice = findViewById(R.id.cardSojenice);

        LinearLayout menuSearch = findViewById(R.id.menuSearch);
        LinearLayout menuProfile = findViewById(R.id.menuProfile);
        LinearLayout menuSettings = findViewById(R.id.menuSettings);

        LinearLayout menuSchedule = findViewById(R.id.menuSchedule);

        TextView tvTemperatura = findViewById(R.id.tvTemperatura);
        TextView tvVlaznost = findViewById(R.id.tvVlaznost);
        CardView cardStatus = findViewById(R.id.cardStatus);
        TextView tvStatus = findViewById(R.id.tvStatus);

        povuciStvarnePodatkeOVremenu(tvTemperatura, tvVlaznost);
        provjeriIReadnoVrijemeKompleksa(cardStatus, tvStatus);

        prikaziRandomZanimljivost(tvZanimljivostTekst);

        if (cardZanimljivosti != null) {
            cardZanimljivosti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prikaziRandomZanimljivost(tvZanimljivostTekst);
                }
            });
        }

        if (cardPocetnaPromoMamac != null) {
            cardPocetnaPromoMamac.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Istražite skrivene pakete u prodavnici!", Toast.LENGTH_SHORT).show();
                    otvoriShop();
                }
            });
        }


        if (btnIdiNaShop != null) {
            btnIdiNaShop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    otvoriShop();
                }
            });
        }

        if (btnOtvoriKviz != null) {
            btnOtvoriKviz.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, QuizActivity.class);
                    startActivity(intent);
                }
            });
        }

        if (cardJezera != null) {
            cardJezera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prikaziDetaljeAtrakcije(
                            "Tri Panonska Jezera",
                            "Kompleks Panonskih jezera čine tri jezera različite veličine i dubine, a nastala su kao plod vizije pretvaranja slanih izvora u ljekovitu oazu... u samom centru Tuzle.\n\n" +
                                    "Voda u jezerima konstantno cirkuliše i filtrira se najsavremenijim tehničkim metodama kroz pješčane filtere, što obezbjeđuje vrhunsku čistoću. Salinitet vode odgovara morskoj vodi i iznosi od 30 do 40 grama po litru, a bogatstvo mineralima (hloridi, natrijum, kalcijum) daje joj izuzetna ljekovita svojstva.\n\n" +
                                    "Ova jedinstvena ekološka oaza u Evropi idealno je mjesto za osvježenje, plivanje, rekreativne aktivnosti, ali i za balneološki zdravstveni odmor koji pomaže kod reumatskih i disajnih tegoba.",
                            R.drawable.slika_jezera
                    );
                }
            });
        }

        if (cardSlapovi != null) {
            cardSlapovi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prikaziDetaljeAtrakcije(
                            "Slani Slapovi",
                            "Slani slapovi su posebna i izuzetno popularna terapeutska atrakcija unutar kompleksa Panonike. Oni funkcionišu kao jedinstven inhalatorni zdravstveni centar na otvorenom.\n\n" +
                                    "Sastoje se od pet kaskada i tri bazena za kupanje na različitim nivoima, gdje prelijevanje i padanje slane vode simulira prirodne morske valove i slapove te mehanički raspršuje vodu. Ovaj proces stvara bogat i čist prirodni slani aerosol u vazduhu.\n\n" +
                                    "Boravak u neposrednoj blizini slapova i udisanje aerosola izuzetno pogoduje disajnim putevima, ublažavanju astme i bronhitisa, smanjenju stresa, te doprinosi opštoj relaksaciji i revitalizaciji čitavog organizma.",
                            R.drawable.slika_slapovi
                    );
                }
            });
        }

        if (cardSojenice != null) {
            cardSojenice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    prikaziDetaljeAtrakcije(
                            "Arheološki Park - Sojenice",
                            "Arheološki park Neolitsko sojenčko naselje predstavlja prvu i jedinstvenu rekonstrukciju neolitskog života na prostorima Bosne i Hercegovine i šireg regiona jugoistočne Evrope.\n\n" +
                                    "Park je u potpunosti zasnovan na bogatim materijalnim dokazima i arheološkim nalazima iz neolitskog doba (mlađeg kamenog doba) koji su otkriveni direktno na lokalitetima u samom gradu Tuzli, svjedočeći o hiljadama godina neprekidnog života i rane eksploatacije soli na ovom tlu.\n\n" +
                                    "Posjetioci ovdje mogu vidjeti vjerodostojne nastambe na drvenim stubovima iznad vode, unutrašnje uređenje kuća, replike neolitskih alata, oruđa, oružja i posuđa, što predstavlja izuzetan kulturno-istorijski i edukativni dodatak cjelokupnoj ponudi kompleksa.",
                            R.drawable.slika_sojenice
                    );
                }
            });
        }

        if (menuSearch != null) {
            menuSearch.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                overridePendingTransition(0, 0);
            });
        }

        if (menuProfile != null) {
            menuProfile.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                overridePendingTransition(0, 0);
            });
        }

        if (menuSettings != null) {
            menuSettings.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                overridePendingTransition(0, 0);
            });
        }

        // --- DODANO ZA OTVARANJE RASPOREDA ---
        if (menuSchedule != null) {
            menuSchedule.setOnClickListener(v -> {
                startActivity(new Intent(MainActivity.this, ScheduleActivity.class));
                overridePendingTransition(0, 0);
            });
        }
    }

    private void provjeriIliZatrazitiDozvoluZaNotifikacije() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                zakaziRandomPodsjetnik();
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        } else {
            zakaziRandomPodsjetnik();
        }
    }

    private void zakaziRandomPodsjetnik() {
        Intent intent = new Intent(this, PodsjetnikReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 10);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (alarmManager != null) {
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, // Ponavlja se automatski svaka 24 sata
                    pendingIntent
            );
        }
    }

    private void otvoriShop() {
        Intent intent = new Intent(MainActivity.this, ShopActivity.class);
        startActivity(intent);
    }

    private void prikaziRandomZanimljivost(TextView tvTekst) {
        if (tvTekst == null) return;
        String[] zanimljivosti = {
                "Slana jezera u Tuzli su jedina vještačka slana jezera u Evropi sa ljekovitim svojstvima.",
                "Voda u Panonskim jezerima ima salinitet sličan morskoj vodi (30-40 g/l).",
                "Arheološki park predstavlja rekonstrukciju neolitskog sojenčkog naselja pronađenog u Tuzli.",
                "Tuzla je jedinstven grad u Evropi koji ima slana jezera, slapove i solanu u samom centru grada.",
                "Slani slapovi služe kao prirodni inhalatorni centar na otvorenom za disajne puteve.",
                "Prvo Panonsko jezero otvoreno je 2003. godine i od tada je privuklo milione posjetilaca.",
                "Historijsko naslijeđe Tuzle direktno je vezano za eksploataciju slane vode i izvora još iz predhistorijskog doba.",
                "Kompleks Panonskih jezera dobitnik je brojnih priznanja za turizam i očuvanje okoliša.",
                "Tokom ljetne sezone, Panonika nudi bogat kulturni, zabavni i sportski sadržaj za sve generacije."
        };
        int index = new java.util.Random().nextInt(zanimljivosti.length);
        tvTekst.setText(zanimljivosti[index]);
    }

    private void povuciStvarnePodatkeOVremenu(TextView tvTemp, TextView tvVlaz) {
        String url = "https://api.open-meteo.com/v1/forecast?latitude=44.5384&longitude=18.6671&current=temperature_2m,relative_humidity_2m";
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.has("current")) {
                                JSONObject currentObject = response.getJSONObject("current");

                                double trenutnaTemp = currentObject.getDouble("temperature_2m");
                                tvTemp.setText(trenutnaTemp + "°C");

                                int trenutnaVlaznost = currentObject.getInt("relative_humidity_2m");
                                tvVlaz.setText("Vlažnost: " + trenutnaVlaznost + "%");
                            } else {
                                tvTemp.setText("--°C");
                                tvVlaz.setText("Vlažnost: --%");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            tvTemp.setText("--°C");
                            tvVlaz.setText("Vlažnost: --%");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvTemp.setText("--°C");
                tvVlaz.setText("Nema veze");
                Toast.makeText(MainActivity.this, "Greška pri osvježavanju podataka uživo", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void provjeriIReadnoVrijemeKompleksa(CardView cardStat, TextView tvStat) {
        Calendar calendar = Calendar.getInstance();
        int mjesec = calendar.get(Calendar.MONTH);
        int sat = calendar.get(Calendar.HOUR_OF_DAY);

        boolean isSezona = (mjesec >= 4 && mjesec <= 8);
        boolean isRadnoVrijeme = (sat >= 8 && sat < 20);

        if (isSezona && isRadnoVrijeme) {
            tvStat.setText("● OTVORENO");
            tvStat.setTextColor(Color.parseColor("#166534"));
            cardStat.setCardBackgroundColor(Color.parseColor("#DCFCE7"));
        } else {
            tvStat.setText("● ZATVORENO");
            tvStat.setTextColor(Color.parseColor("#475569"));
            cardStat.setCardBackgroundColor(Color.parseColor("#E2E8F0"));
        }
    }

    private void prikaziDetaljeAtrakcije(String naslov, String opis, int slikaResId) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_detalji, null);
        bottomSheetDialog.setContentView(dialogView);

        ImageView ivSlika = dialogView.findViewById(R.id.ivDijalogSlika);
        TextView tvNaslov = dialogView.findViewById(R.id.tvDijalogNaslov);
        TextView tvOpis = dialogView.findViewById(R.id.tvDijalogOpis);
        Button btnZatvori = dialogView.findViewById(R.id.btnDijalogZatvori);

        if (tvNaslov != null) tvNaslov.setText(naslov);
        if (tvOpis != null) tvOpis.setText(opis);
        if (ivSlika != null) ivSlika.setImageResource(slikaResId);

        if (btnZatvori != null) {
            btnZatvori.setOnClickListener(v -> bottomSheetDialog.dismiss());
        }
        bottomSheetDialog.show();
    }
}