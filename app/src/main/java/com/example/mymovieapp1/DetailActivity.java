package com.example.mymovieapp1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovieapp1.adapters.ReviewAdapter;
import com.example.mymovieapp1.adapters.TrailerAdapter;
import com.example.mymovieapp1.models.Movie;
import com.example.mymovieapp1.models.Review;
import com.example.mymovieapp1.models.Trailer;
import com.example.mymovieapp1.utils.Constants;
import com.example.mymovieapp1.viewmodels.FavoriteMovieViewModel;
import com.example.mymovieapp1.viewmodels.ReviewViewModel;
import com.example.mymovieapp1.viewmodels.TrailerViewModel;

import java.util.List;

public class DetailActivity extends BaseActivity implements TrailerAdapter.OnTrailerListener {

    Toolbar toolbar2;
    ImageView posterMovie;
    TextView titleMovie;
    TextView voteAverage;
    TextView releaseDate;
    TextView moviePlot;
    ReviewAdapter reviewAdapter;
    RecyclerView reviewRecyclerView;
    ReviewViewModel reviewViewModel;
    TrailerAdapter trailerAdapter;
    RecyclerView tralerRecyclerView;
    TrailerViewModel trailerViewModel;
    Button favButton;
    List<Trailer> trailerList;
    FavoriteMovieViewModel favoriteMovieViewModel;
    Movie movie;
    private boolean movieIsInFav = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        favoriteMovieViewModel = ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);

        toolbar2 = findViewById(R.id.toolbar2);
        toolbar2.setTitle("Movie Details");
        setSupportActionBar(toolbar2);

        favButton = findViewById(R.id.heart_button);
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movieIsInFav) {
                    favoriteMovieViewModel.insert(movie);
                } else {
                    favoriteMovieViewModel.delete(movie);
                }
                Toast.makeText(DetailActivity.this, movieIsInFav ? "Deleted from favorites" : "Added to Favorites", Toast.LENGTH_SHORT).show();
                movieIsInFav = !movieIsInFav;
                changeFavIcon();
            }
        });

        posterMovie = findViewById(R.id.poster_movie);
        titleMovie = findViewById(R.id.title_movie);
        voteAverage = findViewById(R.id.votes_id);
        releaseDate = findViewById(R.id.release_date);
        moviePlot = findViewById(R.id.plot_movie);
        reviewRecyclerView = findViewById(R.id.review_recycler_view);
        tralerRecyclerView = findViewById(R.id.recycler_view_trailer);

        getIncomingIntent();
        subscribeObservers();
        trailerObservers();
        intReviewRecylerView();
        initTrailerRecyclerView();
        isInFavList();

    }

    private boolean isInFavList() {
        favoriteMovieViewModel.loadAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                if (movieList == null) {
                    movieIsInFav = false;
                } else {
                    for (Movie item : movieList) {
                        if (item.getId() == movie.getId()) {
                            movieIsInFav = true;
                        }
                    }

                }
                changeFavIcon();
            }
        });
        return movieIsInFav;
    }

    private void changeFavIcon() {
        favButton.setBackgroundResource(movieIsInFav ? R.drawable.favorite_icon : R.drawable.unfavorite_heart);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("movie")) {
            movie = getIntent().getParcelableExtra("movie");
            setDetails(movie);
            initReviews(movie);
            initTrailers(movie);
        }
    }


    private void subscribeObservers() {
        reviewViewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                if (reviews != null) {
                    reviewAdapter.setReviewList(reviews);
                }
            }
        });
    }

    private void trailerObservers() {
        trailerViewModel.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                if (trailers != null) {
                    trailerAdapter.setTrailerList(trailers);
                }
            }
        });

    }

    private void intReviewRecylerView() {
        reviewAdapter = new ReviewAdapter(DetailActivity.this);
        reviewRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewRecyclerView.setAdapter(reviewAdapter);

    }

    private void initTrailerRecyclerView() {
        trailerAdapter = new TrailerAdapter(trailerList, this);
        tralerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tralerRecyclerView.setAdapter(trailerAdapter);
    }

    private void initReviews(Movie movie) {
        reviewViewModel.searchReviews(movie.getId(), 1);

    }

    private void initTrailers(Movie movie) {
        trailerViewModel.searchTrailers(movie.getId(), 1);

    }

    private void setDetails(Movie movie) {
        if (movie != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(Constants.IMAGE_BASE_URL + movie.getPoster_path())
                    .into(posterMovie);

            titleMovie.setText(movie.getTitle());
            releaseDate.setText(movie.getRelease_date());
            voteAverage.setText(String.valueOf(movie.getVote_average()));
            moviePlot.setText(movie.getOverview());

        }
    }

    @Override
    public void onItemClick(String trailerUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
        startActivity(intent);
    }
}

