package com.example.pannonicatime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.pannonicatime.model.User;

@Dao
public interface UserDao {
    @Insert
    void dodajUsera(User user);

    @Update
    void azurirajUsera(User user);

    @Query("SELECT * FROM users WHERE id = 1 LIMIT 1")
    User dajGlavnogUsera();
}