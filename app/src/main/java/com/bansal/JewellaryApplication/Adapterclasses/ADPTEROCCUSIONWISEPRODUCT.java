package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.Occusionfullproductdetails;
import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJOOCCUSIONWISEPRODUCT;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.DownsampleStrategy;

import org.w3c.dom.Text;

import java.util.List;

public class ADPTEROCCUSIONWISEPRODUCT extends RecyclerView.Adapter<ADPTEROCCUSIONWISEPRODUCT.VIEWHOLDER> {
    List<POJOOCCUSIONWISEPRODUCT> pojooccusionwiseproducts;
    Activity activity;

    public ADPTEROCCUSIONWISEPRODUCT(List<POJOOCCUSIONWISEPRODUCT> pojooccusionwiseproducts, Activity activity) {
        this.pojooccusionwiseproducts = pojooccusionwiseproducts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ADPTEROCCUSIONWISEPRODUCT.VIEWHOLDER onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.deginforshowingproduct,parent,false);
        return new VIEWHOLDER(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADPTEROCCUSIONWISEPRODUCT.VIEWHOLDER holder, int position) {

        POJOOCCUSIONWISEPRODUCT obj=pojooccusionwiseproducts.get(position);
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
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
                SharedPreferences.Editor editor = preferences.edit();

                Intent i=new Intent(activity, Occusionfullproductdetails.class);
                i.putExtra("productID",obj.getProductId());
                editor.putString("occusionid",obj.getProductId());
                editor.apply();
                activity.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pojooccusionwiseproducts.size();
    }

    public class VIEWHOLDER extends RecyclerView.ViewHolder {
        ImageView ivimage;
        TextView tvname,tvprice,tvweight,tvkaret;
        CardView cvcard;
        public VIEWHOLDER(@NonNull View itemView) {
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
