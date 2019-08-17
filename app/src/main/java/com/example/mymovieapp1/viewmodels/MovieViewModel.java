package com.example.mymovieapp1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mymovieapp1.models.Movie;
import com.example.mymovieapp1.repositories.MovieResporitory;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private MovieResporitory movieResporitory;

    public MovieViewModel() {
        movieResporitory = MovieResporitory.getInstance();
    }

    public LiveData<List<Movie>> getMovies() {
        return movieResporitory.getMovies();
    }

    public void searchMoviesApi(String query, int pageNumber) {
        movieResporitory.searchMoviesApi(query, pageNumber);

    }

    public void showPopularMovies(int pageNumber) {
        movieResporitory.showPopularMovies(pageNumber);
    }


    public void showTopRatedMovies(int pageNumber) {
        movieResporitory.showTopRatedMovies(pageNumber);
    }

}
