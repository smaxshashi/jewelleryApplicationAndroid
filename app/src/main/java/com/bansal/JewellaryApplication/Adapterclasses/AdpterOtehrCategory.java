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

import com.bansal.JewellaryApplication.OtthercategoryListProductActivity;
import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJOOTHERSUBCATEGORY;
import com.bansal.JewellaryApplication.pojoclasses.PojoDiamondProductSubcategorylist;
import com.bansal.JewellaryApplication.pojoclasses.PojoOtehrcategory;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

import java.util.List;

public class AdpterOtehrCategory extends RecyclerView.Adapter<AdpterOtehrCategory.ViewHolder> {
    List<PojoOtehrcategory> pojoOtehrcategoryList;

    public AdpterOtehrCategory(List<PojoOtehrcategory> pojoOtehrcategoryList, Activity activity) {
        this.pojoOtehrcategoryList = pojoOtehrcategoryList;
        this.activity = activity;
    }

    Activity activity;
    @NonNull
    @Override
    public AdpterOtehrCategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.deginforshowingproduct,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterOtehrCategory.ViewHolder holder, int position) {
        PojoOtehrcategory obj=pojoOtehrcategoryList.get(position);
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
        return pojoOtehrcategoryList.size();
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
