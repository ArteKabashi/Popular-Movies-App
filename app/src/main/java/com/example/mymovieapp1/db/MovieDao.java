package com.example.mymovieapp1.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.mymovieapp1.models.Movie;

import java.util.List;
@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> loadAllMovies();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertFavMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavMovie(Movie[] movie);

    @Delete
    void deleteMovieFromFav(Movie movie);

    @Query("SELECT * FROM movie WHERE id= :id")
    LiveData<Movie> loadMovieById(int id);

}


