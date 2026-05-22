package com.example.pannonicatime.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {
    @PrimaryKey @NonNull public String uid;
    public String ime;
    public String email;

    public User(@NonNull String uid, String ime, String email) {
        this.uid = uid;
        this.ime = ime;
        this.email = email;
    }
}