package com.bansalandsons.JewellaryApplication.Adapterclasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bansalandsons.JewellaryApplication.R;
import com.bansalandsons.JewellaryApplication.pojoclasses.POJOgetPrice;

import java.util.List;

public class AdpterGetPrice extends RecyclerView.Adapter<AdpterGetPrice.ViewHolder> {

    private List<POJOgetPrice> prices;
    private Context context;

    // Constructor to initialize the list of prices and the context
    public AdpterGetPrice(List<POJOgetPrice> prices, Context context) {
        this.prices = prices;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item
        View view = LayoutInflater.from(context).inflate(R.layout.rvmarketlist, parent, false);
        return new ViewHolder(view); // Return the ViewHolder object
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the price data for the current position
        POJOgetPrice price = prices.get(position);

        // Bind the data to the corresponding views (using the original IDs)
        holder.metalName.setText(price.getMetalName());
        holder.price24K.setText(price.getPrice24K());
        holder.price22K.setText(price.getPrice22K());
        holder.price18K.setText(price.getPrice18K());
        holder.price14K.setText(price.getPrice14K());
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the list
        return prices.size();
    }

    // ViewHolder class to hold the views for each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView metalName, price24K, price22K, price18K, price14K;

        public ViewHolder(View itemView) {
            super(itemView);

            // Initialize the TextViews for the views (using the original IDs from the XML layout)
            metalName = itemView.findViewById(R.id.tvmetalname);
            price24K = itemView.findViewById(R.id.tv24k);
            price22K = itemView.findViewById(R.id.tv22k);
            price18K = itemView.findViewById(R.id.tv18k);
            price14K = itemView.findViewById(R.id.tv14k);
        }
    }
}

