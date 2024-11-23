package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.ActionBar;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETLIST;

import java.util.List;

public class ADPTERGETDATA extends RecyclerView.Adapter<ADPTERGETDATA.ViewHolder> {

    List<POJOGETLIST> pojogetlists;
    Activity activity;

    public ADPTERGETDATA(List<POJOGETLIST> pojogetlists, Activity activity) {
        this.pojogetlists = pojogetlists;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ADPTERGETDATA.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.rvlistofproductdesgin,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADPTERGETDATA.ViewHolder holder, int position) {
        POJOGETLIST obj=pojogetlists.get(position);
        holder.tvname.setText(obj.getName());

    }

    @Override
    public int getItemCount() {
        return pojogetlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname=itemView.findViewById(R.id.tvnamecategory);
        }
    }
}
