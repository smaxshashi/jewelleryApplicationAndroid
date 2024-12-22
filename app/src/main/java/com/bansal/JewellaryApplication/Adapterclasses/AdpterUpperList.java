package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.PojoUpperClass;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdpterUpperList extends RecyclerView.Adapter<AdpterUpperList.ViewHolder> {
    List<PojoUpperClass>pojoUpperClasses;
    Activity activity;

    public AdpterUpperList(List<PojoUpperClass> pojoUpperClasses, Activity activity) {
        this.pojoUpperClasses = pojoUpperClasses;
        this.activity = activity;
    }

    @NonNull
    @Override
    public AdpterUpperList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(activity).inflate(R.layout.circleimage,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdpterUpperList.ViewHolder holder, int position) {
        PojoUpperClass obj=pojoUpperClasses.get(position);
        holder.tvname.setText(obj.getName());
        Glide.with(activity)
                .load(obj.getImage())
                .skipMemoryCache(true)
                .error(R.drawable.noimage)
                .into(holder.cvimage);

    }

    @Override
    public int getItemCount() {
        return pojoUpperClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView cvimage;
        TextView tvname;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvname=itemView.findViewById(R.id.civname);
            cvimage=itemView.findViewById(R.id.circleimage);
        }
    }
}
