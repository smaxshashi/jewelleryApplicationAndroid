package com.bansalandsons.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansalandsons.JewellaryApplication.OccusionProduct;
import com.bansalandsons.JewellaryApplication.R;
import com.bansalandsons.JewellaryApplication.pojoclasses.POJOOccusion;
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
             .skipMemoryCache(true)  // Skip memory cache 
                .error(R.drawable.noimage)
                .into(holder.ivimage);

        holder.ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=activity.getSharedPreferences("occusion", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                Intent i = new Intent(activity, OccusionProduct.class);
                editor.putString("Occusion",obj.getName());
                editor.apply();
                i.putExtra("occusionname",obj.getName());
                activity.startActivity(i);
            }
        });


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
