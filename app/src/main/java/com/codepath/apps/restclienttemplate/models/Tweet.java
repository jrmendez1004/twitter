package com.codepath.apps.restclienttemplate.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Tweet {
    public String body;
    public String createdAt;
    public User user;
    //public boolean hasMedia;
    //public List<String> embeddedMedia;

    //creates tweet object from jsonObject
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.body = jsonObject.getString("full_text");
        tweet.createdAt = tweet.getRelativeTimeAgo(jsonObject.getString("created_at"));
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        /*JSONObject entities = jsonObject.getJSONObject("entities");
        if(entities.has("media")){
            JSONArray media = entities.getJSONArray("media");
            tweet.hasMedia = true;
            tweet.embeddedMedia = new ArrayList<>();
            for(int i = 0; i < media.length(); i++)
                tweet.embeddedMedia.add(media.getJSONObject(i).getString("media_url_https"));

        }
        else{
            tweet.hasMedia = false;
            tweet.embeddedMedia = new ArrayList<>();
        }*/

        return tweet;
    }

    //creates a list of tweets to be displayed
    public static List<Tweet> fromJsonArray(JSONArray jsonArray) throws JSONException{
        List<Tweet> tweets = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++)
            tweets.add(fromJson(jsonArray.getJSONObject(i)));

        return tweets;
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        try {
            long time = sf.parse(rawJsonDate).getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + "m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + "h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + "d";
            }
        } catch (ParseException e) {
            Log.i("Tweet", "getRelativeTimeAgo failed");
            e.printStackTrace();
        }

        return "";
    }
}
