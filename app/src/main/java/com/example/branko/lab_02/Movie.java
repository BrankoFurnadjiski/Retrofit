package com.example.branko.lab_02;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Branko on 12.11.2017.
 */

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "name")
    String Title;

    @ColumnInfo
    String Year;

    @ColumnInfo
    String Poster;
}