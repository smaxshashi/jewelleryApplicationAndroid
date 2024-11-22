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
import com.bansal.JewellaryApplication.pojoclasses.POJOOccusion;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdpterOccusion extends RecyclerView.Adapter<AdpterOccusion.ViewHolder> {

    List<POJOOccusion> pojoOccusions;
    Activity activity;

    public AdpterOccusion(List<POJOOccusion> pojoOccusions, Activity activity) {
        this.pojoOccusions = pojoOccusions;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterOccusion.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvcategorydesgin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterOccusion.ViewHolder holder, int position) {
        POJOOccusion obj=pojoOccusions.get(position);
        holder.tvname.setText(obj.getName());
        Glide.with(activity)
                .load(obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .into(holder.ivimage);


    }

    @Override
    public int getItemCount() {
        return pojoOccusions.size();
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
