package com.example.mymovieapp1.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovieapp1.R;
import com.example.mymovieapp1.models.Movie;
import com.example.mymovieapp1.utils.Constants;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Movie>movieList;
    private OnMovieListener onMovieListener;

    public MovieAdapter(OnMovieListener onMovieListener) {
        this.onMovieListener = onMovieListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_movie_item,viewGroup,false);
        return new MovieViewHolder(view,onMovieListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(((MovieViewHolder) viewHolder).itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Constants.IMAGE_BASE_URL+ movieList.get(i).getPoster_path())
                .into(((MovieViewHolder) viewHolder).posterMovie);

        ((MovieViewHolder) viewHolder).titleMovie.setText(movieList.get(i).getTitle());
        ((MovieViewHolder) viewHolder).averageVote.setText(String.valueOf(movieList.get(i).getVote_average()));


    }

    @Override
    public int getItemCount() {
        if (movieList!=null){
            return movieList.size();
        }
        return 0;
    }

    public void setMovieList(List<Movie>movieList){
        this.movieList=movieList;
        notifyDataSetChanged();
    }

    public  Movie getSelectedMovieDetails(int position){
        if (movieList !=null){
            if (movieList.size()>0){
                return movieList.get(position);
            }
        }
        return null;
    }


}
