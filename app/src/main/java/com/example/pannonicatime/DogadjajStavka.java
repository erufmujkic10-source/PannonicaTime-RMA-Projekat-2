package com.example.pannonicatime;

public class DogadjajStavka {
    private String dan;
    private String naziv;
    private String vrijeme;
    private String opis;
    private int ikonaResurs;

    public DogadjajStavka(String dan, String naziv, String vrijeme, String opis, int ikonaResurs) {
        this.dan = dan;
        this.naziv = naziv;
        this.vrijeme = vrijeme;
        this.opis = opis;
        this.ikonaResurs = ikonaResurs;
    }

    public String getDan() { return dan; }
    public String getNaziv() { return naziv; }
    public String getVrijeme() { return vrijeme; }
    public String getOpis() { return opis; }
    public int getIkonaResurs() { return ikonaResurs; }
}