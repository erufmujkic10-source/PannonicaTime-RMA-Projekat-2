package com.example.pannonicatime.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.pannonicatime.model.User;
import com.example.pannonicatime.model.Proizvod;
import com.example.pannonicatime.model.Narudzba;

@Database(entities = {User.class, Proizvod.class, Narudzba.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProizvodDao proizvodDao();
    public abstract NarudzbaDao narudzbaDao();

    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "panonica_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}