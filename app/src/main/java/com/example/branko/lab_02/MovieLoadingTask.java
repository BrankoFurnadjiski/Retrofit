package com.example.branko.lab_02;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Branko on 14.11.2017.
 */

class MovieLoadingTask extends AsyncTask<Void, Void, List<Movie>> {

    private MoviesApi service;
    private RecyclerAdapter adapter;
    private Map<String, String> attributes;

    public MovieLoadingTask(RecyclerAdapter adapter, String movieName) {
        this.adapter = adapter;
        this.attributes = new HashMap<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(MoviesApi.class);

        attributes.put("apikey", "f1dee3a0");
        attributes.put("s", movieName);
    }

    @Override
    protected void onPreExecute() {
        // Common scenario: Start progress dialog
        super.onPreExecute();
    }

    @Override
    protected List<Movie> doInBackground(Void... params) {
        Call<SearchResult> call = service.getMovies(attributes);
        try {
            SearchResult result = call.execute().body();
            return result.getSearchResult();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        super.onPostExecute(movies);
        adapter.setData(movies);
        adapter.notifyDataSetChanged();
    }
}