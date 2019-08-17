package com.example.mymovieapp1.request;

import com.example.mymovieapp1.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private  static MovieApi movieApi = retrofit.create(MovieApi.class);

    public  static MovieApi getMovieApi() {
        return movieApi;
    }

}
