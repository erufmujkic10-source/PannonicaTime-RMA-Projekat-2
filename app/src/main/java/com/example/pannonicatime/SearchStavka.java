package com.example.pannonicatime;

public class SearchStavka {
    private String naslov;
    private String opis;
    private String tip;
    private double cijenaNumericki;
    private int slikaResId;
    private int kolicina;

    public SearchStavka(String naslov, String opis, String tip, double cijenaNumericki, int slikaResId) {
        this.naslov = naslov;
        this.opis = opis;
        this.tip = tip;
        this.cijenaNumericki = cijenaNumericki;
        this.slikaResId = slikaResId;
        this.kolicina = 0;
    }

    public String getNaslov() { return naslov; }
    public String getOpis() { return opis; }
    public String getTip() { return tip; }
    public double getCijenaNumericki() { return cijenaNumericki; }
    public int getSlikaResId() { return slikaResId; }
    public int getKolicina() { return kolicina; }
    public void setKolicina(int kolicina) { this.kolicina = kolicina; }
}