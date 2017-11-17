package com.example.branko.lab_02;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Branko on 14.11.2017.
 */

public interface MoviesApi {
    @GET("/")
    Call<SearchResult> getMovies(@QueryMap Map<String, String> attributes);

    @GET("/")
    Call<MovieDetails> getMovieDetails(@QueryMap Map<String, String> attributes);
}
