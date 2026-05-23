package com.example.pannonicatime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.pannonicatime.model.Proizvod;
import java.util.List;

@Dao
public interface ProizvodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void dodajProizvod(Proizvod p);

    @Update
    void azurirajProizvod(Proizvod p);

    @Query("SELECT * FROM proizvodi WHERE naziv = :naziv")
    Proizvod dajProizvodPoNazivu(String naziv);

    @Query("SELECT * FROM proizvodi")
    List<Proizvod> dajSveProizvode();
}