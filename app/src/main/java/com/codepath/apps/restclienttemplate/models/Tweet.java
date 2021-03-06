package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Parcel
public class Tweet {
    public String body;
    public String createdAt;
    public String date;
    public User user;

    public boolean hasMedia;
    public List<String> embeddedMedia;

    public Tweet(){

    }

    //creates tweet object from jsonObject
    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        if(jsonObject.has("full_text"))
            tweet.body = jsonObject.getString("full_text");
        else
            tweet.body = jsonObject.getString("text");
        tweet.createdAt = tweet.getRelativeTimeAgo(jsonObject.getString("created_at"));
        tweet.createdAt = tweet.getDateCreated(jsonObject.getString("created_at"));
        tweet.user = User.fromJson(jsonObject.getJSONObject("user"));

        if (!jsonObject.isNull("entities")) {
            JSONObject entities = jsonObject.getJSONObject("entities");
            if(entities.has("media")){
                JSONArray media = entities.getJSONArray("media");
                tweet.hasMedia = true;
                tweet.embeddedMedia = new ArrayList<>();
                for(int i = 0; i < media.length(); i++) {
                    String temp = String.format("%s:large", media.getJSONObject(i).getString("media_url_https"));
                    tweet.embeddedMedia.add(temp);
                }
                tweet.body = tweet.removeUrl(tweet.body);
            }
            else{
                tweet.hasMedia = false;
                tweet.embeddedMedia = new ArrayList<>();
            }
        } else {
            tweet.hasMedia = false;
            tweet.embeddedMedia = new ArrayList<>();
        }

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

    public String getDateCreated(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    private String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        return commentstr;
    }
}
