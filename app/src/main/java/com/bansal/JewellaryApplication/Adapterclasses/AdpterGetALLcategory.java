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
import com.bansal.JewellaryApplication.pojoclasses.POJOGetallcategory;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterGetALLcategory extends RecyclerView.Adapter<AdpterGetALLcategory.ViewHolder> {

    List<POJOGetallcategory> pojoGetallcategories;
    Activity activity;

    public AdpterGetALLcategory(List<POJOGetallcategory> pojoGetallcategories, Activity activity) {
        this.pojoGetallcategories = pojoGetallcategories;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterGetALLcategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvcategorydesgin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterGetALLcategory.ViewHolder holder, int position) {
        POJOGetallcategory obj = pojoGetallcategories.get(position);
        holder.tvcategoryname.setText(obj.getCategoryname());
        Glide.with(activity)
                .load(obj.getCategoryImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)// Resize the image to 800x800 pixels
                .into(holder.ivimage);



    }

    @Override
    public int getItemCount() {
        return pojoGetallcategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivimage;
        TextView tvcategoryname;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivimage=itemView.findViewById(R.id.categoryImage);
            tvcategoryname = itemView.findViewById(R.id.categoryName);
        }
    }
}
