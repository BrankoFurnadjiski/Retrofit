package com.example.branko.lab_02;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by Branko on 26.11.2017.
 */

@Database(entities = {Movie.class}, version = 1)
public abstract class MoviesDatabase extends RoomDatabase {

    private static MoviesDatabase INSTANCE;

    public abstract MovieDao movieDao();

    public static MoviesDatabase getMoviesDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    MoviesDatabase.class,
                    "movies").build();
        }
        return INSTANCE;
    }

    public static void onDestroyInstance() {
        INSTANCE = null;
    }

}
