package com.example.mymovieapp1.request.responses;

import com.example.mymovieapp1.models.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {
    @SerializedName("page")
    private int page;

    @SerializedName("total_results")
    private int totalResults;

    @SerializedName("total_pages")
    private int totalPages;

    @SerializedName("results")
    private List<Movie> movieResults;

    public List<Movie> getMovieResults() {
        return movieResults;
    }
}
