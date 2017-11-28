package com.example.branko.lab_02;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Branko on 26.11.2017.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    Context context;

    public MovieLoader(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public List<Movie> loadInBackground() {
        MoviesDatabase db = MoviesDatabase.getMoviesDatabase(context);
        return db.movieDao().getAll();
    }

}
