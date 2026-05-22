package com.example.pannonicatime.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.pannonicatime.model.User;
import com.example.pannonicatime.model.Proizvod;

@Database(entities = {User.class, Proizvod.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProizvodDao proizvodDao();

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, "panonica_db").build();
        }
        return instance;
    }
}