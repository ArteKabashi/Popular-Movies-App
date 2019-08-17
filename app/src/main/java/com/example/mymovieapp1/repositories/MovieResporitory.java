package com.example.mymovieapp1.repositories;

import android.arch.lifecycle.LiveData;

import com.example.mymovieapp1.models.Movie;
import com.example.mymovieapp1.models.Review;
import com.example.mymovieapp1.models.Trailer;
import com.example.mymovieapp1.request.MovieApiClient;

import java.util.List;

public class MovieResporitory {

    private static MovieResporitory instance;
    private MovieApiClient movieApiClient;

    public static MovieResporitory getInstance() {
        if (instance == null) {
            instance = new MovieResporitory();
        }
        return instance;
    }

    private MovieResporitory() {
        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieApiClient.getMovies();
    }

    public void searchMoviesApi(String query, int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        movieApiClient.searchMoviesApi(query, pageNumber);
    }


    public void showPopularMovies(int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        movieApiClient.showPopularMovies(pageNumber);
    }


    public void showTopRatedMovies(int pageNumber) {
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        movieApiClient.showTopRatedMovies(pageNumber);
    }

    public LiveData<List<Review>> getReviews(){
        return movieApiClient.getReviews();
    }

    public void searchReviews(int id,int pageNumber) {
        if (pageNumber==0){
            pageNumber=1;
        }
        movieApiClient.searchReviews(id,pageNumber);
    }


    public LiveData<List<Trailer>>getTrailers(){
        return movieApiClient.getTrailers();
    }

    public void searchTrailers(int id,int pageNumber){
        if (pageNumber==0){
            pageNumber=1;
        }
     movieApiClient.searchTrailers(id, pageNumber);
    }

}
