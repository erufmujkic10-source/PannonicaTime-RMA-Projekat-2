package com.example.pannonicatime.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.example.pannonicatime.model.User;

@Dao
public interface UserDao {

    @Insert
    void registrujKorisnika(User user);


    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

   //da se ne moze isti mejl vise puta koristiti ovo ne dirati
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User provjeriEmail(String email);
}