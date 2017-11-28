package com.example.branko.lab_02;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Branko on 26.11.2017.
 */

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies c ORDER BY c.name")
    List<Movie> getAll();

    @Insert
    void insert(Movie users);

    @Insert
    void insertAll(Movie... users);

    @Update
    void update(Movie users);

    @Delete
    void delete(Movie user);

    @Query("DELETE FROM movies")
    void deleteAll();

}
