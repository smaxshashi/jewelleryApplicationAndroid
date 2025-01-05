package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.DimaondMenWOmen;
import com.bansal.JewellaryApplication.GetCategoryProductSubnot;
import com.bansal.JewellaryApplication.GetCategoryWiseProduct;
import com.bansal.JewellaryApplication.GetMenOrWomenProduct;
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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvcategorydesgin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        POJOGetallcategory obj = pojoGetallcategories.get(position);
        holder.tvcategoryname.setText(obj.getCategoryname());
        Glide.with(activity)
                .load(obj.getCategoryImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .into(holder.ivimage);

        holder.ivimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoryCode = obj.getCategorycode(); // Get the category code

                // Check if the category code is missing or invalid
                if (categoryCode == null || categoryCode.isEmpty()) {
                    Toast.makeText(activity, "Category code is missing!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save the category code using SharedPreferences

                Intent intent;
                // Use .equals() to compare strings
                if (categoryCode.equals("4001") ) {
                    intent = new Intent(activity, GetMenOrWomenProduct.class);

                } else if ( categoryCode.equals("4002")) {
                    intent = new Intent(activity, DimaondMenWOmen.class);

                } else if (categoryCode.equals("4003") || categoryCode.equals("4004") ||
                        categoryCode.equals("4005") || categoryCode.equals("4006")) {
                    intent = new Intent(activity, GetCategoryWiseProduct.class);

               }else if (categoryCode.equals("4007")
                ||  categoryCode.equals("4008") ||  categoryCode.equals("4009")) {
                    intent = new Intent(activity, GetCategoryProductSubnot.class);

               } else {
                    // Handle any other unexpected codes
                    Toast.makeText(activity, "Unknown category code: " + categoryCode, Toast.LENGTH_SHORT).show();
                    return;
                }

                intent.putExtra("CategoryCode", categoryCode);
                intent.putExtra("categoryname",obj.getCategoryname());
                activity.startActivity(intent);
            }
        });

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

            ivimage = itemView.findViewById(R.id.categoryImage);
            tvcategoryname = itemView.findViewById(R.id.categoryName);
        }
    }
}

