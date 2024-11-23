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
import com.bansal.JewellaryApplication.pojoclasses.POJOMenWomenProductlist;

import java.util.List;

public class AdpterGetMenWomenProductlist extends RecyclerView.Adapter<AdpterGetMenWomenProductlist.ViewHolder> {
    List<POJOMenWomenProductlist> pojoMenWomenProductlists;
    Activity activity;

    public AdpterGetMenWomenProductlist(List<POJOMenWomenProductlist> pojoMenWomenProductlists, Activity activity) {
        this.pojoMenWomenProductlists = pojoMenWomenProductlists;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterGetMenWomenProductlist.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.rvmarketlist,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterGetMenWomenProductlist.ViewHolder holder, int position) {
        POJOMenWomenProductlist obj=pojoMenWomenProductlists.get(position);
        holder.tvnameproduct.setText(obj.getProductname());

    }

    @Override
    public int getItemCount() {
        return pojoMenWomenProductlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivproductimage;
        TextView tvnameproduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvnameproduct=itemView.findViewById(R.id.tvnamecategory);
        }
    }
}
