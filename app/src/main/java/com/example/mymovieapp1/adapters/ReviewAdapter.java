package com.example.mymovieapp1.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mymovieapp1.R;
import com.example.mymovieapp1.models.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    private List<Review> reviewList;
    private Context context;

    public ReviewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_item,viewGroup,false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder reviewViewHolder, int i) {
        reviewViewHolder.userName.setText(reviewList.get(i).getAuthor());
        reviewViewHolder.userComment.setText(reviewList.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        if (reviewList!=null){
            return reviewList.size();
        }
        return 0;
    }


    public void setReviewList(List<Review> reviewList){
        this.reviewList=reviewList;
        notifyDataSetChanged();
    }


    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        TextView userComment;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.reviewer_name_item);
            userComment=itemView.findViewById(R.id.review_comment_id);

        }
    }

}
