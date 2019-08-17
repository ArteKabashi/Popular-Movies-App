package com.example.mymovieapp1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.mymovieapp1.models.Review;
import com.example.mymovieapp1.repositories.MovieResporitory;

import java.util.List;

public class ReviewViewModel extends ViewModel {

    private MovieResporitory movieResporitory;

    public ReviewViewModel() {
        movieResporitory = MovieResporitory.getInstance();
    }

    public LiveData<List<Review>> getReviews() {
        return movieResporitory.getReviews();
    }

    public void searchReviews(int  id ,int pageNumber) {
        movieResporitory.searchReviews(id,pageNumber);
    }

}
