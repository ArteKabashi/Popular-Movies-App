package com.example.mymovieapp1.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymovieapp1.R;


public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView posterMovie;
    TextView titleMovie;
    TextView averageVote;
    private OnMovieListener onMovieListener;

    public MovieViewHolder(@NonNull View itemView, OnMovieListener onMovieListener) {
        super(itemView);

        this.onMovieListener = onMovieListener;

        posterMovie = itemView.findViewById(R.id.movieImage);
        titleMovie = itemView.findViewById(R.id.titleMovie);
        averageVote = itemView.findViewById(R.id.votes);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onMovieListener.onMovieClick(getAdapterPosition());

    }
}
