package com.example.mymovieapp1.request;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.example.mymovieapp1.AppExecutors;
import com.example.mymovieapp1.models.Movie;
import com.example.mymovieapp1.models.Review;
import com.example.mymovieapp1.models.Trailer;
import com.example.mymovieapp1.request.responses.MovieResponse;
import com.example.mymovieapp1.request.responses.ReviewResponse;
import com.example.mymovieapp1.request.responses.SearchMovieResponse;
import com.example.mymovieapp1.request.responses.TrailerResponse;
import com.example.mymovieapp1.utils.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.mymovieapp1.utils.Constants.NETWORK_TIMEOUT;

public class MovieApiClient {


    private static final String TAG = "MovieApiClient";

    private static MovieApiClient instance;
    private MutableLiveData<List<Movie>> mMovies;
    private RetriveMoviesRunnable retriveMoviesRunnable;


    public static MovieApiClient getInstance() {
        if (instance == null) {
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient() {
        mMovies = new MutableLiveData<>();
        mReviews=new MutableLiveData<>();
        mTrailers=new MutableLiveData<>();
    }

    public LiveData<List<Movie>> getMovies() {
        return mMovies;
    }

    public void searchMoviesApi(String query, int pageNumber) {
        if (retriveMoviesRunnable != null) {
            retriveMoviesRunnable = null;
        }
        retriveMoviesRunnable = new RetriveMoviesRunnable(query, pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(retriveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

    private class RetriveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetriveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getSearchResults(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<Movie> list = new ArrayList<>(((SearchMovieResponse) response.body()).getSearchResults());
                    if (pageNumber == 1) {
                        mMovies.postValue(list);
                    } else {
                        List<Movie> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }

        }

        private Call<SearchMovieResponse> getSearchResults(String query, int pageNumber) {
            return ServiceGenerator.getMovieApi().searchResults(
                    Constants.API_KEY, query, pageNumber);
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelReuest: canceling the search request");
            cancelRequest = true;
        }

    }

    ///////////////////////////////////////////////////////////////

    private RetrivePopularMovies retrivePopularMovies;

    public void showPopularMovies(int pageNumber) {
        if (retrivePopularMovies != null) {
            retrivePopularMovies = null;
        }
        retrivePopularMovies = new RetrivePopularMovies(pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(retrivePopularMovies);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    private class RetrivePopularMovies implements Runnable {
        private int pageNumber;
        private boolean cancelRequest;

        public RetrivePopularMovies(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovieResults(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.isSuccessful()) {
                    List<Movie> movieList1 = new ArrayList<>(((MovieResponse) response.body()).getMovieResults());
                    if (pageNumber == 1) {
                        mMovies.postValue(movieList1);
                    } else {
                        List<Movie> currentPopularMovies = mMovies.getValue();
                        currentPopularMovies.addAll(movieList1);
                        mMovies.postValue(currentPopularMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run error " + error);
                    mMovies.postValue(null);
                }
            } catch (Exception e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        private Call<MovieResponse> getMovieResults(int pageNumber) {
            return ServiceGenerator.getMovieApi().getMovieResults(
                    Constants.API_KEY, pageNumber);
        }


        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the retrieval query");
            cancelRequest = true;
        }
    }

/////////////////////

    private RetriveTopRatedMovies retriveTopRatedMovies;

    public void showTopRatedMovies(int pageNumber) {
        if (retriveTopRatedMovies != null) {
            retriveTopRatedMovies = null;
        }
        retriveTopRatedMovies = new RetriveTopRatedMovies(pageNumber);
        final Future handler = AppExecutors.getInstance().networkIO().submit(retriveTopRatedMovies);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    private class RetriveTopRatedMovies implements Runnable {
        private int pageNumber;
        private boolean cancelRequest;

        public RetriveTopRatedMovies(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovieResults(pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.isSuccessful()) {
                    List<Movie> movieList2 = new ArrayList<>(((MovieResponse) response.body()).getMovieResults());
                    if (pageNumber == 1) {
                        mMovies.postValue(movieList2);
                    } else {
                        List<Movie> currentTopRatedMovies = mMovies.getValue();
                        currentTopRatedMovies.addAll(movieList2);
                        mMovies.postValue(currentTopRatedMovies);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run error");
                    mMovies.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);
            }
        }

        private Call<MovieResponse> getMovieResults(int pageNumber) {
            return ServiceGenerator.getMovieApi().topRatedMovieResponse(
                    Constants.API_KEY, pageNumber);
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the Top Rated query");
            cancelRequest = true;
        }
    }
    /////////////////////////////////////

    private MutableLiveData<List<Review>>mReviews;
    private RetriveReviewRunnable reviewRetriveMoviesRunnable;

    public LiveData<List<Review>> getReviews(){
        return mReviews;
    }

    public void searchReviews(int id,int pageNumber){
        if (reviewRetriveMoviesRunnable!=null){
            reviewRetriveMoviesRunnable=null;
        }
        reviewRetriveMoviesRunnable=new RetriveReviewRunnable(id,pageNumber);

        final Future handler=AppExecutors.getInstance().networkIO().submit(reviewRetriveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        },NETWORK_TIMEOUT,TimeUnit.MILLISECONDS);

    }

    public class RetriveReviewRunnable implements Runnable {
        private int id;
        private int pageNumber;
        private boolean cancelRequest;

        public RetriveReviewRunnable(int id,int pageNumber) {
            this.id = id;
            this.pageNumber=pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getReviewResults(id, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.isSuccessful()) {
                    List<Review> reviewList = new ArrayList<>(((ReviewResponse) response.body()).getReviewResults());
                    if (pageNumber == 1) {
                        mReviews.postValue(reviewList);
                    } else {
                        List<Review> currentReviews = mReviews.getValue();
                        currentReviews.addAll(reviewList);
                        mReviews.postValue(currentReviews);

                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "onResponse: " + error);
                    mReviews.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mReviews.postValue(null);
            }
        }

        private Call<ReviewResponse> getReviewResults(int id,int pageNumber) {
            return ServiceGenerator.getMovieApi().getReviews(
                   id,Constants.API_KEY,pageNumber);
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the review query");
            cancelRequest = true;
        }
    }

    /////////////////////////////////////////

    private MutableLiveData<List<Trailer>>mTrailers;
    private RetriveTrailersRunnable retriveTrailersRunnable;

    public LiveData<List<Trailer>>getTrailers(){
        return mTrailers;
    }

    public void searchTrailers(int id,int pageNumber){
        if (retriveTrailersRunnable!=null){
            retriveTrailersRunnable=null;
        }
        retriveTrailersRunnable=new RetriveTrailersRunnable(id, pageNumber);

        final Future handler=AppExecutors.getInstance().networkIO().submit(retriveTrailersRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        },NETWORK_TIMEOUT,TimeUnit.MILLISECONDS);

    }


    public class RetriveTrailersRunnable implements Runnable{
       private int id;
       private int pageNumber;
        private boolean cancelRequest;

        public RetriveTrailersRunnable(int id,int pageNumber) {
            this.id = id;
            this.pageNumber=pageNumber;
            cancelRequest=false;
        }

        @Override
        public void run() {
            try {
                Response response=getTrailerResults(id,pageNumber).execute();
                if (cancelRequest){
                    return;
                }
                if (response.isSuccessful()){
                    List<Trailer>trailerList=new ArrayList<>(((TrailerResponse)response.body()).getTrailerResults());
                    if (pageNumber==1){
                        mTrailers.postValue(trailerList);
                    }
                    else {
                        List<Trailer>currentTrailer=mTrailers.getValue();
                        currentTrailer.addAll(trailerList);
                        mTrailers.postValue(currentTrailer);
                    }

                }else {
                    String error=response.errorBody().string();
                    Log.e(TAG,"onResponse: "+error);
                    mTrailers.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mTrailers.postValue(null);
            }

        }

        private Call<TrailerResponse>getTrailerResults(int id,int pageNumber){
            return ServiceGenerator.getMovieApi().getTrailerResponse(
                    id,Constants.API_KEY,pageNumber
            );
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the trailer query");
            cancelRequest = true;
        }

    }


}
