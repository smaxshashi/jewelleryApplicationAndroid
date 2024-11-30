package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.GiftingProduct;
import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJOGifting;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdpterGifting extends RecyclerView.Adapter<AdpterGifting.ViewHolder> {

    private List<POJOGifting> giftingList;
    private Activity activity;

    public AdpterGifting(List<POJOGifting> giftingList, Activity activity) {
        this.giftingList = giftingList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.giftingguiddesgin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        POJOGifting gifting = giftingList.get(position);

        // Bind data to views
        holder.giftingName.setText(gifting.getGiftingName());

        // Load image using Glide
        Glide.with(activity)
                .load(gifting.getExfield1()) // Load image URL from POJO
                .override(200, 200)  // Resize images
                .skipMemoryCache(true)
                .error(R.drawable.noimage) // Error image if loading fails
                .into(holder.giftingImage);

        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, GiftingProduct.class);
                i.putExtra("Gifting",gifting.getGiftingName());
                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return giftingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView giftingName;
        ImageView giftingImage;
        CardView cvcard;

        public ViewHolder(View itemView) {
            super(itemView);
            giftingName = itemView.findViewById(R.id.categoryName);
            giftingImage = itemView.findViewById(R.id.categoryImage); // ImageView for exfield1
            cvcard=itemView.findViewById(R.id.cvGiftingproduct);
        }
    }
}
