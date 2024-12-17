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

public class Adpterimagewishlist extends RecyclerView.Adapter<Adpterimagewishlist.ViewHolder>{
    private Context context;
    private List<String> imageUrls;

    public Adpterimagewishlist(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public Adpterimagewishlist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sooulmetslider, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adpterimagewishlist.ViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);
        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewSoulmate);
        }
    }
}
