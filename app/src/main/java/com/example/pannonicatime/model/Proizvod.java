package com.example.pannonicatime.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "proizvodi")
public class Proizvod {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "naziv")
    private String naziv;

    @ColumnInfo(name = "opis")
    private String opis;

    @ColumnInfo(name = "cijena")
    private double cijena;

    @ColumnInfo(name = "slikaNaziv")
    private String slikaNaziv;

    @ColumnInfo(name = "kolicina")
    private int kolicina;

    public Proizvod() {}

    public Proizvod(String naziv, String opis, double cijena, String slikaNaziv) {
        this.naziv = naziv;
        this.opis = opis;
        this.cijena = cijena;
        this.slikaNaziv = slikaNaziv;
        this.kolicina = 0;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNaziv() { return naziv; }
    public void setNaziv(String naziv) { this.naziv = naziv; }
    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
    public double getCijena() { return cijena; }
    public void setCijena(double cijena) { this.cijena = cijena; }
    public String getSlikaNaziv() { return slikaNaziv; }
    public void setSlikaNaziv(String slikaNaziv) { this.slikaNaziv = slikaNaziv; }
    public int getKolicina() { return kolicina; }
    public void setKolicina(int kolicina) { this.kolicina = kolicina; }
}