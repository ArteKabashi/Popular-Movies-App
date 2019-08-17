package com.example.mymovieapp1.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mymovieapp1.models.Movie;
import com.example.mymovieapp1.repositories.FavoriteRepository;

import java.util.List;


public class FavoriteMovieViewModel extends AndroidViewModel {

    private FavoriteRepository favoriteRepository;
    public LiveData<List<Movie>> listLiveData;

    public FavoriteMovieViewModel(@NonNull Application application) {
        super(application);

        favoriteRepository = new FavoriteRepository(application);
        listLiveData = favoriteRepository.loadAllMovies();

    }

    public LiveData<List<Movie>> loadAllMovies() {
        return listLiveData;
    }

    public void insert(Movie movie) {
        favoriteRepository.insertMovie(movie);
    }

    public void delete(Movie movie) {
        favoriteRepository.deleteMovie(movie);
    }

}



