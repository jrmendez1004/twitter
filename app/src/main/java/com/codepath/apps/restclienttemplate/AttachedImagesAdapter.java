package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AttachedImagesAdapter extends RecyclerView.Adapter<AttachedImagesAdapter.ViewHolder> {
    Context context;
    List<String> images;

    public AttachedImagesAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_attached, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = images.get(position);
        holder.Bind(image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView attachedImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attachedImage = itemView.findViewById(R.id.ivAttached);
        }

        public void Bind(String image){
            Glide.with(context).load(image).into(attachedImage);
        }
    }
}
