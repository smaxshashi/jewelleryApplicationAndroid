package com.bansalandsons.JewellaryApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImagesliderAdapter extends RecyclerView.Adapter<ImagesliderAdapter.ImageViewholder> {
    private Context context;
    private List<String> imageUrls;

    public ImagesliderAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public ImagesliderAdapter.ImageViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sliderimage, parent, false);
        return new ImageViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesliderAdapter.ImageViewholder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ImageViewholder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ImageViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
