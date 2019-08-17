package com.example.mymovieapp1.request.responses;

import com.example.mymovieapp1.models.Trailer;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResponse {


    private int id;
    @SerializedName("results")
    private List<Trailer>trailerResults;

    public int getId() {
        return id;
    }

    public List<Trailer> getTrailerResults() {
        return trailerResults;
    }
}
