package com.bansalandsons.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bansalandsons.JewellaryApplication.HimorherFullProductdettail;
import com.bansalandsons.JewellaryApplication.R;
import com.bansalandsons.JewellaryApplication.pojoclasses.POJOGEThimHerProduct;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

import java.util.List;

public class ADPTERGetHimORHerProduct extends RecyclerView.Adapter<ADPTERGetHimORHerProduct.ViewHolder> {

    List<POJOGEThimHerProduct> pojogeThimHerProducts;
    Activity activity;

    public ADPTERGetHimORHerProduct(List<POJOGEThimHerProduct> pojogeThimHerProducts, Activity activity) {
        this.pojogeThimHerProducts = pojogeThimHerProducts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ADPTERGetHimORHerProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.deginforshowingproduct,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADPTERGetHimORHerProduct.ViewHolder holder, int position) {
        POJOGEThimHerProduct obj=pojogeThimHerProducts.get(position);
        holder.tvname.setText(obj.getProductName());
        holder.tvprice.setText(obj.getDescription());
        holder.tvweight.setText(obj.getWeight());
        holder.tvkaret.setText(obj.getKarat());
        Glide.with(activity)
                .load(obj.getImageUrl())
             .skipMemoryCache(true)  // Skip memory cache 
                .error(R.drawable.noimage)
                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                .override(800, 800) // Resize the image to 800x800 pixels
                .into(holder.ivimage);
        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, HimorherFullProductdettail.class);
                i.putExtra("productId",obj.getProductId());
                i.putExtra("soulmet",obj.getSoulmet());
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojogeThimHerProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tvname,tvprice,tvweight,tvkaret;
        CardView cvcard;
        public ViewHolder(@NonNull View itemView) {
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
