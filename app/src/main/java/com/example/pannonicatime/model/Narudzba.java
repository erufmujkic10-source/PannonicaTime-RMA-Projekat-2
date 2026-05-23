package com.example.pannonicatime.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "narudzbe")
public class Narudzba {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String korisnikEmail;

    public String detalji;
    public double ukupnaCijena;
    public String datum;

    public Narudzba() {}

    public Narudzba(String korisnikEmail, String detalji, double ukupnaCijena, String datum) {
        this.korisnikEmail = korisnikEmail;
        this.detalji = detalji;
        this.ukupnaCijena = ukupnaCijena;
        this.datum = datum;
    }
}