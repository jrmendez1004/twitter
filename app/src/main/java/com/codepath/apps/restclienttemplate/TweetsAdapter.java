package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{
    Context context;
    List<Tweet> tweets;

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tweet tweet = tweets.get(position);
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivProfileImage;
        TextView body;
        TextView screen_name;
        TextView createdAt;
        //RecyclerView attached_images;
        List<String> attImages;
        AttachedImagesAdapter adapter;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProfileImage = itemView.findViewById(R.id.ivProfileImage);
            body = itemView.findViewById(R.id.tvBody);
            screen_name = itemView.findViewById(R.id.tvScreenName);
            createdAt = itemView.findViewById(R.id.tvCreatedAt);

            //attached_images = itemView.findViewById(R.id.rvAttachedImages);
            adapter = new AttachedImagesAdapter(context, attImages);
        }

        public void bind(Tweet tweet) {
            body.setText(tweet.body);
            screen_name.setText(tweet.user.screenName);
            createdAt.setText(tweet.createdAt);
            Glide.with(context).load(tweet.user.profileImageUrl).into(ivProfileImage);

            /*if(tweet.hasMedia) {
                if(tweet.embeddedMedia.size() > 1)
                    attached_images.setLayoutManager(new GridLayoutManager(context, 2));
                else
                    attached_images.setLayoutManager(new GridLayoutManager(context, 1));
                attached_images.setAdapter(adapter);
                for(int i = 0; i < tweet.embeddedMedia.size(); i++)
                    attImages.add(tweet.embeddedMedia.get(i));
            }
            adapter.notifyItemInsert();*/
        }
    }
}
