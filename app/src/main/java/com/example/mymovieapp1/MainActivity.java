package com.example.mymovieapp1;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;


import com.example.mymovieapp1.adapters.MovieAdapter;
import com.example.mymovieapp1.adapters.OnMovieListener;
import com.example.mymovieapp1.models.Movie;
import com.example.mymovieapp1.utils.Constants;
import com.example.mymovieapp1.viewmodels.FavoriteMovieViewModel;
import com.example.mymovieapp1.viewmodels.MovieViewModel;

import java.util.List;

public class MainActivity extends BaseActivity implements OnMovieListener {

    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private MovieViewModel movieViewModel;
    private FavoriteMovieViewModel favoriteMovieViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        recyclerView = findViewById(R.id.recycler_view_main);
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        favoriteMovieViewModel=ViewModelProviders.of(this).get(FavoriteMovieViewModel.class);

        initRecyclerView();
        subscribeObservers();
        initSearchView();
        initPopularMovieView();
    }

    private void subscribeObservers() {
        movieViewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                if (movieList != null) {
                    movieAdapter.setMovieList(movieList);
                }
            }
        });
    }


    private void initFsvViewModel() {
        favoriteMovieViewModel.loadAllMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movieList) {
                movieAdapter.setMovieList(movieList);
            }
        });

    }


    private void initRecyclerView() {
        movieAdapter = new MovieAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, Constants.NUM_OF_COLUMNS);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(movieAdapter);
    }

    private void initPopularMovieView() {
        movieViewModel.showPopularMovies(1);
    }

    private void initTopRatedMoviesView() {
        movieViewModel.showTopRatedMovies(1);
    }

    private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                movieViewModel.searchMoviesApi(s, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        switch (menuItemSelected) {
            case R.id.popular_item:
                initPopularMovieView();
                Toast.makeText(this, "Popular Movies", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.tooRated_item:
                initTopRatedMoviesView();
                Toast.makeText(this, "Top Rated Movies", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.favorite_item:
                Toast.makeText(this, "Favorite Movies", Toast.LENGTH_SHORT).show();
                initFsvViewModel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onMovieClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("movie", movieAdapter.getSelectedMovieDetails(position));
        startActivity(intent);
    }
}
