package com.bansal.JewellaryApplication.Adapterclasses;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bansal.JewellaryApplication.OtthercategoryListProductActivity;
import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJODIMAONDPRODUCTLIST;
import com.bansal.JewellaryApplication.pojoclasses.POJOOTHERSUBCATEGORY;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ADPterSubcategory extends RecyclerView.Adapter<ADPterSubcategory.ViewHolder> {
    private List<POJOOTHERSUBCATEGORY> dataList;
    private Activity context;

    public ADPterSubcategory(List<POJOOTHERSUBCATEGORY> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ADPterSubcategory.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rvlistofproductdesgin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ADPterSubcategory.ViewHolder holder, int position) {
        POJOOTHERSUBCATEGORY obj=dataList.get(position);
        holder.nameTextView.setText(obj.getName());

        Glide.with(context)
                .load(obj.getImage())
               .onlyRetrieveFromCache(true) 
                .error(R.drawable.noimage)
                .into(holder.cvImage);

        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, OtthercategoryListProductActivity.class);
                i.putExtra("subcategorycode",obj.getCode());
                i.putExtra("subcategory",obj.getName());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void updateData(List<POJOOTHERSUBCATEGORY> newData) {
        this.dataList.clear();
        this.dataList.addAll(newData);
        notifyDataSetChanged();  // Notify adapter that data has changed
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        CardView cvcard;
        CircleImageView cvImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.tvnamecategory);
            cvcard = itemView.findViewById(R.id.cvProductcard);
            cvImage=itemView.findViewById(R.id.ListofProductimage);
        }
    }
}
