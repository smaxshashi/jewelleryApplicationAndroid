package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.GetGoldSubproductActivity;
import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETDATAMENGOLD;

import java.util.ArrayList;
import java.util.List;

public class ADPTERGOLDMENDATA extends RecyclerView.Adapter<ADPTERGOLDMENDATA.ViewHolder> {
    private List<POJOGETDATAMENGOLD> dataList;
    private Activity context;

    public ADPTERGOLDMENDATA(List<POJOGETDATAMENGOLD> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rvlistofproductdesgin, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        POJOGETDATAMENGOLD item = dataList.get(position);
        holder.nameTextView.setText(item.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, GetGoldSubproductActivity.class);
                i.putExtra("name",item.getName());
                context.startActivity(i);
            }
        });
        // Bind other data...
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    // This method is used to update the adapter's dataset
    public void updateData(List<POJOGETDATAMENGOLD> newData) {
        this.dataList.clear();
        this.dataList.addAll(newData);
        notifyDataSetChanged();  // Notify adapter that data has changed
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvnamecategory);
            cardView = itemView.findViewById(R.id.cvProductcard);
            // Initialize other views...
        }
    }
}

