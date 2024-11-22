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
import com.bansal.JewellaryApplication.pojoclasses.POJOgetCTaegorygenderwiseproduct;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterGetcategorygenderproduct extends RecyclerView.Adapter<AdpterGetcategorygenderproduct.Viewholder> {
    List<POJOgetCTaegorygenderwiseproduct> pojOgetCTaegorygenderwiseproducts;
    Activity activity;

    public AdpterGetcategorygenderproduct(List<POJOgetCTaegorygenderwiseproduct> pojOgetCTaegorygenderwiseproducts, Activity activity) {
        this.pojOgetCTaegorygenderwiseproducts = pojOgetCTaegorygenderwiseproducts;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterGetcategorygenderproduct.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvlistofproductdesgin,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterGetcategorygenderproduct.Viewholder holder, int position) {
        POJOgetCTaegorygenderwiseproduct obj=pojOgetCTaegorygenderwiseproducts.get(position);
        holder.tvname.setText(obj.getName());

    }

    @Override
    public int getItemCount() {
        return pojOgetCTaegorygenderwiseproducts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ImageView cvimage;
        TextView tvname;
        CardView cvcard;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            cvcard=itemView.findViewById(R.id.cvcard);
            tvname=itemView.findViewById(R.id.tvnamecategory);
            cvimage=itemView.findViewById(R.id.ivImageofproduct);
        }
    }
}
