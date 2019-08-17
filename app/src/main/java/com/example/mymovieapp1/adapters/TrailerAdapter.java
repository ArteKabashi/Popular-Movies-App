package com.example.mymovieapp1.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.mymovieapp1.R;
import com.example.mymovieapp1.models.Trailer;
import com.example.mymovieapp1.utils.Constants;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailerList;
    private OnTrailerListener onTrailerListener;

    public interface OnTrailerListener{
        void onItemClick(String trailerUrl);
    }

    public TrailerAdapter(List<Trailer>trailerList,OnTrailerListener onTrailerListener) {
        this.onTrailerListener = onTrailerListener;
        this.trailerList=trailerList;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.trailer_item, viewGroup, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {

        RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.ic_launcher_background);

        Glide.with(trailerViewHolder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(Constants.YOUTUBE_THUMBNAIL_BASE_URL + trailerList.get(i).getKey() + Constants.YOUTUBE_THUMBNAIL_URL_JPG)
                .into(trailerViewHolder.trailerImage);
    }

    @Override
    public int getItemCount() {
        if (trailerList != null) {
            return trailerList.size();
        }
        return 0;
    }


    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView trailerImage;
        ImageButton trailerPlayButton;


        public TrailerViewHolder(@NonNull View itemView) {
            super(itemView);

            trailerImage = itemView.findViewById(R.id.trailer_image);
            trailerPlayButton = itemView.findViewById(R.id.play_trailer_button);
            trailerPlayButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition=getAdapterPosition();
            Trailer trailer=trailerList.get(adapterPosition);
            String trailerKey=trailer.getKey();
            String trailerUrl=Constants.YOUTUBE_BASE_URL+trailerKey;
            onTrailerListener.onItemClick(trailerUrl);

        }
    }
}
