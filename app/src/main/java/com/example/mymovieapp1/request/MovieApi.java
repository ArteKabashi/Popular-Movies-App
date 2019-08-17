package com.example.mymovieapp1.request;

import com.example.mymovieapp1.request.responses.MovieResponse;
import com.example.mymovieapp1.request.responses.ReviewResponse;
import com.example.mymovieapp1.request.responses.SearchMovieResponse;
import com.example.mymovieapp1.request.responses.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// /movie/popular or  /movie/top_rated
//  /movie/{id}/videos
//  /movie/{id}/reviews
// /movie/popular

public interface MovieApi {

    //SEARCH
    @GET("search/movie")
    Call<SearchMovieResponse> searchResults(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") int page
    );

    //Popular Movies
    @GET("movie/popular")
    Call<MovieResponse> getMovieResults(
            @Query("api_key") String key,
            @Query("page") int page
    );

    //Top-rated Movies
    @GET("movie/top_rated")
    Call<MovieResponse> topRatedMovieResponse(
            @Query("api_key") String key,
            @Query("page") int page
    );


    @GET("movie/{id}/videos")
    Call<TrailerResponse> getTrailerResponse(
            @Path("id") int id,
            @Query("api_key") String key,
            @Query("page")int page
    );

    //Reviews
    @GET("movie/{id}/reviews")
    Call<ReviewResponse> getReviews(
            @Path("id") int id,
            @Query("api_key") String key,
            @Query("page") int page


    );

}
