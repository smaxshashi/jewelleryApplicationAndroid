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
import com.bansal.JewellaryApplication.pojoclasses.PojoDiamondProductSubcategorylist;
import com.bansal.JewellaryApplication.pojoclasses.PojoGoldProduct;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

import java.util.List;

public class AdpterDiamondSubcategoryProduct extends RecyclerView.Adapter<AdpterDiamondSubcategoryProduct.ViewHolder> {
    List<PojoDiamondProductSubcategorylist> pojoDiamondProductSubcategorylists;
    Activity activity;

    public AdpterDiamondSubcategoryProduct(List<PojoDiamondProductSubcategorylist> pojoDiamondProductSubcategorylists, Activity activity) {
        this.pojoDiamondProductSubcategorylists = pojoDiamondProductSubcategorylists;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterDiamondSubcategoryProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.deginforshowingproduct,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterDiamondSubcategoryProduct.ViewHolder holder, int position) {
        PojoDiamondProductSubcategorylist obj=pojoDiamondProductSubcategorylists.get(position);
        holder.tvname.setText(obj.getProductName());
        holder.tvprice.setText(obj.getProductName());
        holder.tvweight.setText(obj.getWeight());
        holder.tvkaret.setText(obj.getKarat());
        Glide.with(activity)
                .load(obj.getImageUrl())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .downsample(DownsampleStrategy.CENTER_INSIDE) // Scale down image to fit within specified bounds
                .override(800, 800) // Resize the image to 800x800 pixels
                .into(holder.ivimage);

    }

    @Override
    public int getItemCount() {
        return pojoDiamondProductSubcategorylists.size();
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