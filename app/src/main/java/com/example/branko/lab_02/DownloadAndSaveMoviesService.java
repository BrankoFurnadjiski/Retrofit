package com.example.branko.lab_02;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Branko on 26.11.2017.
 */

public class DownloadAndSaveMoviesService extends IntentService {

    public static final String DATABASE_UPDATED = "com.example.branko.lab_02.DATABASE_UPDATED";

    private MoviesApi moviesApi;
    private String movieName = "";
    private Map<String, String> attributes;

    public DownloadAndSaveMoviesService() {
        super("Download and Save movies");
        attributes = new HashMap<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.moviesApi = retrofit.create(MoviesApi.class);

        attributes.put("apikey", "f1dee3a0");
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart(intent, startId);
        movieName = intent.getStringExtra("MovieName");
        attributes.put("s", movieName);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Call<SearchResult> call = moviesApi.getMovies(attributes);
        try {
            SearchResult result = call.execute().body();

            saveResultsInDb(result.getSearchResult());

            //setDataLoadedPref();

            this.sendBroadcast(new Intent(DATABASE_UPDATED));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void saveResultsInDb(List<Movie> result) {
        MoviesDatabase db = MoviesDatabase.getMoviesDatabase(this);

        db.movieDao().deleteAll();
        db.movieDao()
                .insertAll(result.toArray(new Movie[]{}));

    }

   /* private void setDataLoadedPref() {
        SharedPreferences prefs = this.getSharedPreferences(MoviesActivity.WEB_LOADING_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean(MoviesActivity.IS_DATA_LOADED, true);

        editor.commit();

    }*/

}
