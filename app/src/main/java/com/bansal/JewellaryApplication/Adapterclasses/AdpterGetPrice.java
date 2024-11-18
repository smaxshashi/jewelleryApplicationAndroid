package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJOgetPrice;

import java.util.List;

public class AdpterGetPrice extends RecyclerView.Adapter<AdpterGetPrice.ViewHolder> {
    List<POJOgetPrice> pojOgetPrices;
    Activity activity;

    public AdpterGetPrice(List<POJOgetPrice> pojOgetPrices, Activity activity) {
        this.pojOgetPrices = pojOgetPrices;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterGetPrice.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvmarketlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterGetPrice.ViewHolder holder, int position) {
        POJOgetPrice obj = pojOgetPrices.get(position);
        holder.tvmetalname.setText(obj.getMetal());
        holder.k24.setText(obj.getK24());
        holder.k22.setText(obj.getK22());
        holder.k18.setText(obj.getK18());
        holder.k14.setText(obj.getK14());
    }

    @Override
    public int getItemCount() {
        return pojOgetPrices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvmetalname, k24, k22, k18, k14;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvmetalname = itemView.findViewById(R.id.tvmetalname);
            k24 = itemView.findViewById(R.id.tv24k);
            k22 = itemView.findViewById(R.id.tv22k);
            k18 = itemView.findViewById(R.id.tv18k);
            k14 = itemView.findViewById(R.id.tv14k);
        }
    }
}

