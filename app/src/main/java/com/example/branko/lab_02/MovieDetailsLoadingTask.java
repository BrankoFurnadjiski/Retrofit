package com.example.branko.lab_02;

import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Branko on 14.11.2017.
 */

class MovieDetailsLoadingTask extends AsyncTask<Void, Void, MovieDetails> {

    private MoviesApi service;
    private Map<String, String> attributes;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewGenre;
    private TextView textViewActors;
    private TextView textViewPlot;

    public MovieDetailsLoadingTask(View view, String movieName) {
        this.attributes = new HashMap<>();
        textViewActors = view.findViewById(R.id.textViewActors);
        textViewTitle = view.findViewById(R.id.textViewTitle);
        textViewYear = view.findViewById(R.id.textViewYear);
        textViewGenre = view.findViewById(R.id.textViewGenre);
        textViewPlot = view.findViewById(R.id.textViewPlot);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.omdbapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.service = retrofit.create(MoviesApi.class);

        attributes.put("apikey", "f1dee3a0");
        attributes.put("t", movieName);
        attributes.put("plot", "full");
    }

    @Override
    protected void onPreExecute() {
        // Common scenario: Start progress dialog
        super.onPreExecute();
    }

    @Override
    protected MovieDetails doInBackground(Void... params) {
        Call<MovieDetails> call = service.getMovieDetails(attributes);
        try {
            MovieDetails result = call.execute().body();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // POSSIBLE NULL POINTER EXCEPTION
    }

    @Override
    protected void onPostExecute(MovieDetails movie) {
        super.onPostExecute(movie);
        textViewActors.setText("Actors: " + movie.Actors);
        textViewGenre.setText("Genre: " + movie.Genre);
        textViewPlot.setText(movie.Plot);
        textViewTitle.setText(movie.Title);
        textViewYear.setText("Year: " + movie.Year);
    }
}