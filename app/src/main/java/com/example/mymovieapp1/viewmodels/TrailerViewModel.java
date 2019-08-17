package com.example.mymovieapp1.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.example.mymovieapp1.models.Trailer;
import com.example.mymovieapp1.repositories.MovieResporitory;

import java.util.List;

public class TrailerViewModel extends ViewModel {

    private MovieResporitory movieResporitory;

    public TrailerViewModel() {
        movieResporitory=MovieResporitory.getInstance();
    }

    public LiveData<List<Trailer>> getTrailers(){
        return movieResporitory.getTrailers();
    }

    public void searchTrailers(int id,int pageNumber){
        movieResporitory.searchTrailers(id, pageNumber);
    }

}
