package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJODiamondSubProduct;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

import java.util.List;

public class AdpterDiamonSubProduct extends RecyclerView.Adapter<AdpterDiamonSubProduct.ViewHolder> {
    List<POJODiamondSubProduct> pojoDiamondSubProducts;
    Activity activity;

    public AdpterDiamonSubProduct(List<POJODiamondSubProduct> pojoDiamondSubProducts, Activity activity) {
        this.pojoDiamondSubProducts = pojoDiamondSubProducts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterDiamonSubProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.deginforshowingproduct,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterDiamonSubProduct.ViewHolder holder, int position) {
        POJODiamondSubProduct obj=pojoDiamondSubProducts.get(position);
        holder.tvname.setText(obj.getProductName());
        holder.tvprice.setText(obj.getProductName());
        holder.tvweight.setText(obj.getWeight());
        holder.tvkaret.setText(obj.getKarat());
        Glide.with(activity)
                .load(obj.getImageUrl())
                .onlyRetrieveFromCache(true) // Load only from cache; skip network
                .error(R.drawable.noimage)
                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                .override(800, 800) // Resize the image to 800x800 pixels
                .into(holder.ivimage);

    }

    @Override
    public int getItemCount() {
        return pojoDiamondSubProducts.size();
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
