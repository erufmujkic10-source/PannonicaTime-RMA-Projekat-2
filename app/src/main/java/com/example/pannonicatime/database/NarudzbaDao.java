package com.example.pannonicatime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.pannonicatime.model.Narudzba;
import java.util.List;

@Dao
public interface NarudzbaDao {
    @Insert
    void dodajNarudzbu(Narudzba narudzba);

    @Query("SELECT * FROM narudzbe ORDER BY id DESC")
    List<Narudzba> dajSveNarudzbe();

    @Query("SELECT * FROM narudzbe WHERE korisnikEmail = :email ORDER BY id DESC")
    List<Narudzba> dajNarudzbeZaKorisnika(String email);
}