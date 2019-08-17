package com.example.mymovieapp1.repositories;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.mymovieapp1.db.MovieDao;
import com.example.mymovieapp1.db.MovieDatabase;
import com.example.mymovieapp1.models.Movie;

import java.util.List;

public class FavoriteRepository {
    private MovieDao movieDao;
    private LiveData<List<Movie>> allMovies;

    public FavoriteRepository(Application application) {
        MovieDatabase movieDatabase = MovieDatabase.getInstance(application);
        movieDao = movieDatabase.movieDao();
        allMovies = movieDao.loadAllMovies();
    }

   public LiveData<List<Movie>> loadAllMovies() {
        return allMovies;
    }

    ///

    public void insertMovie(Movie movie) {
        new PopulateDbAsync(movieDao).execute(movie);
    }

    private static class PopulateDbAsync extends AsyncTask<Movie, Void, Void> {

        private MovieDao asynckMovieDao;

        PopulateDbAsync(MovieDao dao) {
            asynckMovieDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movies) {
            Movie[] m = movies;
            asynckMovieDao.insertFavMovie(movies[0]);
            return null;
        }
    }


    public void deleteMovie(Movie movie) {
        new DeleteDbAsync(movieDao).execute(movie);
    }


    private static class DeleteDbAsync extends AsyncTask<Movie, Void, Void> {

        private MovieDao asynckMovieDao;

        DeleteDbAsync(MovieDao dao) {
            asynckMovieDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... movies) {

            Movie[] m = movies;
            asynckMovieDao.deleteMovieFromFav(movies[0]);
            return null;
        }

    }
}


