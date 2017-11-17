package com.example.branko.lab_02;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Branko on 14.11.2017.
 */


class SearchResult {
    @SerializedName("Search")
    private List<Movie> movies;

    public List<Movie> getSearchResult(){
        return movies;
    }

}
