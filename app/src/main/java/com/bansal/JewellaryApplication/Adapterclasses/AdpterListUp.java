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

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.GiftingFulllDetails;
import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.UpperlistProductDetails;
import com.bansal.JewellaryApplication.pojoclasses.POJOGiftingproduct;
import com.bansal.JewellaryApplication.pojoclasses.POjoupperlist;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

import java.util.List;

public class AdpterListUp extends RecyclerView.Adapter<AdpterListUp.Viewholder> {
    List<POjoupperlist> pOjoupperlistList;
    Activity activity;

    public AdpterListUp(List<POjoupperlist> pOjoupperlistList, Activity activity) {
        this.pOjoupperlistList = pOjoupperlistList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterListUp.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.deginforshowingproduct,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterListUp.Viewholder holder, int position) {
        POjoupperlist obj=pOjoupperlistList.get(position);
        holder.tvname.setText(obj.getProductName());
        holder.tvprice.setText(obj.getDescription());
        holder.tvweight.setText(obj.getWeight());
        holder.tvkaret.setText(obj.getKarat());
        holder.tvkaret.setText(obj.getKarat());
        Glide.with(activity)
                .load(obj.getImageUrl())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                .override(800, 800) // Resize the image to 800x800 pixels
                .into(holder.ivimage);

        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, UpperlistProductDetails.class);
                i.putExtra("productId",obj.getProductId());
                activity.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return pOjoupperlistList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tvname,tvprice,tvweight,tvkaret;
        CardView cvcard;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            ivimage=itemView.findViewById(R.id.rvUserFragmentCategoryProductImge);
            tvname=itemView.findViewById(R.id.rvUserFragmentCategoryProductName);
            tvprice=itemView.findViewById(R.id.rvUserFragmentCategoryProductPrice);
            tvweight=itemView.findViewById(R.id.rvUserFragmentCategoryProductWeight);
            tvkaret=itemView.findViewById(R.id.rvUserFragmentCategoryProductkaret);
            cvcard=itemView.findViewById(R.id.rvUserFragmentCategoryProductCard);
        }
    }
}
