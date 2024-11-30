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

import com.bansal.JewellaryApplication.DimontCategoryProduct;
import com.bansal.JewellaryApplication.R;
import com.bansal.JewellaryApplication.pojoclasses.POJODIMAONDPRODUCTLIST;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETDATAMENGOLD;

import java.util.List;

public class ADPTERDIMANDPRODUCTLIST extends RecyclerView.Adapter<ADPTERDIMANDPRODUCTLIST.ViewHolder> {
    private List<POJODIMAONDPRODUCTLIST> dataList;
    private Activity context;

    public ADPTERDIMANDPRODUCTLIST(List<POJODIMAONDPRODUCTLIST> dataList, Activity context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ADPTERDIMANDPRODUCTLIST.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rvlistofproductdesgin, parent, false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ADPTERDIMANDPRODUCTLIST.ViewHolder holder, int position) {
        POJODIMAONDPRODUCTLIST item = dataList.get(position);
        holder.nameTextView.setText(item.getName());

        holder.cvcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DimontCategoryProduct.class);
                i.putExtra("Categoryname",item.getName());
                context.startActivity(i);
            }
        });
        // Bind other data...

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void updateData(List<POJODIMAONDPRODUCTLIST> newData) {
        this.dataList.clear();
        this.dataList.addAll(newData);
        notifyDataSetChanged();  // Notify adapter that data has changed
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        CardView cvcard;

         public ViewHolder(@NonNull View itemView) {
            super(itemView);
             nameTextView = itemView.findViewById(R.id.tvnamecategory);
             cvcard = itemView.findViewById(R.id.cvProductcard);
        }
    }
}
