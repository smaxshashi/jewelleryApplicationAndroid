package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJOSokumate;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdpterSoulmate extends RecyclerView.Adapter<AdpterSoulmate.ViewHolder> {
List<POJOSokumate> pojoSokumates;
Activity activity;

    public AdpterSoulmate(List<POJOSokumate> pojoSokumates, Activity activity) {
        this.pojoSokumates = pojoSokumates;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterSoulmate.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvsolu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterSoulmate.ViewHolder holder, int position) {
        POJOSokumate obj=pojoSokumates.get(position);
        holder.tvname.setText(obj.getName());
        Glide.with(activity)
                .load(obj.getImage())
             .skipMemoryCache(true)  // Skip memory cache 
                .error(R.drawable.noimage)
                .centerInside()
                .centerCrop()
                .into(holder.ivimage);

    }

    @Override
    public int getItemCount() {
        return pojoSokumates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tvname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivimage=itemView.findViewById(R.id.categoryImage);
            tvname=itemView.findViewById(R.id.categoryName);
        }
    }
}
