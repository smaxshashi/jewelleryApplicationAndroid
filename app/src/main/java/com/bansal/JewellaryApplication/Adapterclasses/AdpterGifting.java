package com.bansal.JewellaryApplication.Adapterclasses;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJOGifting;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdpterGifting extends RecyclerView.Adapter<AdpterGifting.ViewHolder> {

    private List<POJOGifting> giftingList;
    Activity activity;

    public AdpterGifting(List<POJOGifting> giftingList, Activity activity) {
        this.giftingList = giftingList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvcategorydesgin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        POJOGifting gifting = giftingList.get(position);
        holder.giftingName.setText(gifting.getGiftingName());
        Glide.with(activity)
                .load(gifting.getExfield1())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.giftingImage);


    }

    @Override
    public int getItemCount() {
        return giftingList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView giftingName, giftingCode;
        ImageView giftingImage;

        public ViewHolder(View itemView) {
            super(itemView);
            giftingName = itemView.findViewById(R.id.categoryName);
            giftingImage = itemView.findViewById(R.id.categoryImage); // Make sure you have an ImageView for exfield1
        }
    }
}
