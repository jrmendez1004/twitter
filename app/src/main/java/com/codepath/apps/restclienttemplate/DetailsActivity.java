package com.codepath.apps.restclienttemplate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    private TextView body;
    private TextView time;
    private ImageView ivProfile;
    private TextView userName;
    private Tweet tweet;
    private ImageView pictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Log.i("Details", "Moved to Details class");

        body = findViewById(R.id.tvBody2);
        time = findViewById(R.id.tvTime);
        ivProfile = findViewById(R.id.ivProfileImage2);
        userName = findViewById(R.id.userName);
        pictures = findViewById(R.id.ivDetailsImage);

        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));

        body.setText(tweet.body);
        time.setText(tweet.createdAt);
        Glide.with(this).load(tweet.user.profileImageUrl).centerInside().transform(new RoundedCorners(100)).into(ivProfile);
        userName.setText(tweet.user.screenName);
        int radius = 30;
        if(tweet.hasMedia)
            Glide.with(this).load(tweet.embeddedMedia.get(0)).centerInside().transform(new RoundedCorners(radius)).into(pictures);
    }
}