package com.example.pannonicatime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.pannonicatime.model.Proizvod;
import java.util.List;

@Dao
public interface ProizvodDao {

    @Insert
    void dodajProizvod(Proizvod proizvod);


    @Query("SELECT * FROM proizvodi")
    List<Proizvod> dajSveProizvode();


    @Query("SELECT * FROM proizvodi WHERE naziv LIKE :pretraga")
    List<Proizvod> pretraziProizvode(String pretraga);
}