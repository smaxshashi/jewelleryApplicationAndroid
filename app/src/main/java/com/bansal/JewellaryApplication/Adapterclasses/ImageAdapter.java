package com.bansal.JewellaryApplication.Adapterclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ArrayList<String> imageUrls;

    public ImageAdapter(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the layout for each image item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        // Get the URL of the image and load it into the ImageView
        String imageUrl = imageUrls.get(position);
        Glide.with(holder.itemView.getContext()).load(imageUrl)
                .centerInside()
              .skipMemoryCache(true)  // Skip memory cache // Load only from cache; skip network
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size(); // Number of items in the slider
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderImageView);  // Get the ImageView from the layout
        }
    }
}

