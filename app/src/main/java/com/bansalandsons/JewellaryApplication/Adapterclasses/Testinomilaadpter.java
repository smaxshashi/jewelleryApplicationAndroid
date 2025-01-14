package com.bansalandsons.JewellaryApplication.Adapterclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansalandsons.JewellaryApplication.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class Testinomilaadpter extends RecyclerView.Adapter<Testinomilaadpter.ImageViewHolder> {
    private Context context;
    private List<String> imageUrls;

    public Testinomilaadpter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public Testinomilaadpter.ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.testinomial, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Testinomilaadpter.ImageViewHolder holder, int position) {
        Glide.with(context)
                .load(imageUrls.get(position))
                .error(R.drawable.noimage) // Set an error image in case loading fails
                .centerInside() // The image URL
              .skipMemoryCache(true)  // Skip memory cache // Load only from cache; skip network
                .override(800, 800) // Resize to 800x600 pixels
                .diskCacheStrategy(DiskCacheStrategy.ALL) // Cache the image for better performance
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
