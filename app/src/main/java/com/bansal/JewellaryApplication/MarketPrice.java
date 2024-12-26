 package com.bansal.JewellaryApplication;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGetPrice;
import com.bansal.JewellaryApplication.pojoclasses.POJOgetPrice;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

 public class MarketPrice extends AppCompatActivity {
RecyclerView rvlist;
List<POJOgetPrice> pojOgetPrices;
AdpterGetPrice adpterGetPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_market_price);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        getWindow().setStatusBarColor(ContextCompat.getColor(MarketPrice.this,R.color.yo));getWindow().setNavigationBarColor(ContextCompat.getColor(MarketPrice.this,R.color.yo));

        rvlist=findViewById(R.id.rvUserHomeFragmentListmaket);
        rvlist.setLayoutManager(new LinearLayoutManager(MarketPrice.this, LinearLayoutManager.VERTICAL, false));
        fetchPrices();


    }

     private void fetchPrices() {
         String url = "https://api.gehnamall.com/api/prices";
         RequestQueue requestQueue = Volley.newRequestQueue(MarketPrice.this); // Initialize the request queue

         pojOgetPrices = new ArrayList<>(); // Initialize the list that will hold your data

         // Set the RecyclerView LayoutManager to a vertical LinearLayoutManager
         rvlist.setLayoutManager(new LinearLayoutManager(MarketPrice.this, LinearLayoutManager.VERTICAL, false));

         // JSON Array request to fetch the data
         JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                 Request.Method.GET, // HTTP GET request
                 url, // The URL of your API
                 null, // No request body
                 new Response.Listener<JSONArray>() {
                     @Override
                     public void onResponse(JSONArray response) {
                         try {
                             pojOgetPrices.clear(); // Clear previous data to prevent duplication

                             // Iterate through each element in the response array
                             for (int i = 0; i < response.length(); i++) {
                                 JSONObject categoryObj = response.getJSONObject(i); // Get each element as a JSONObject
                                 Log.d("API categoryObj", categoryObj.toString()); // Log the object for debugging

                                 // Get the metalName
                                 String strMetalName = categoryObj.optString("metalName", "Unknown");

                                 // Get the price values by using the keys "18K:", "14K:", "24K:", "22K:"
                                 String str18K = categoryObj.optString("18K:", "0.0");
                                 String str14K = categoryObj.optString("14K:", "0.0");
                                 String str24K = categoryObj.optString("24K:", "0.0");
                                 String str22K = categoryObj.optString("22K:", "0.0");

                                 // Add the parsed data into your list
                                 pojOgetPrices.add(new POJOgetPrice(str24K, str22K, str18K, str14K, strMetalName));
                             }

                             // Update the adapter with the new list of prices

                             adpterGetPrice = new AdpterGetPrice(pojOgetPrices, MarketPrice.this);
                             rvlist.setAdapter(adpterGetPrice); // Set the RecyclerView adapter


                         } catch (JSONException e) {
                             e.printStackTrace();
                             Toast.makeText(MarketPrice.this, "Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         Toast.makeText(MarketPrice.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                     }
                 }
         );

         // Add the request to the queue
         requestQueue.add(jsonArrayRequest);
     }
}