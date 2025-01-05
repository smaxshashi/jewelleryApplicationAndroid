package com.bansal.JewellaryApplication.Adapterclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.R;
import com.bumptech.glide.Glide;

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
                .centerInside() // The image URL
                .onlyRetrieveFromCache(true) // Load only from cache; skip network
                .override(800, 800) // Resize to 800x600 pixels
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
