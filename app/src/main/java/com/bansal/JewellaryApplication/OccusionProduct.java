package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGetHimORHerProduct;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTEROCCUSIONWISEPRODUCT;
import com.bansal.JewellaryApplication.pojoclasses.POJOGEThimHerProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOOCCUSIONWISEPRODUCT;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OccusionProduct extends AppCompatActivity {
    RecyclerView rvexplorlist;
    private static final String API_URL = "https://api.gehnamall.com/api/getProducts?occasion=";
    List<POJOOCCUSIONWISEPRODUCT> pojooccusionwiseproducts;
    ADPTEROCCUSIONWISEPRODUCT adpteroccusionwiseproduct;

    String occuion;
    TextView tvoccusion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_occusion_product);

        occuion=getIntent().getStringExtra("occusionname");

        getWindow().setStatusBarColor(ContextCompat.getColor(OccusionProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(OccusionProduct.this, R.color.white));
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);




        rvexplorlist=findViewById(R.id.rvUserExplorList);
        tvoccusion=findViewById(R.id.tvoccusion);

        tvoccusion.setText(occuion);

        rvexplorlist.setLayoutManager(new GridLayoutManager(OccusionProduct.this,2,GridLayoutManager.VERTICAL,false));
        pojooccusionwiseproducts = new ArrayList<>();
        
        fetchproduct(occuion);

    }

    private void fetchproduct(String occuion) {
        String url = API_URL + occuion+"&wholeseller=Test";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 0) { // Check status = 0
                                JSONArray productsArray = response.getJSONArray("products");

                                for (int i = 0; i < productsArray.length(); i++) {
                                    JSONObject productObj = productsArray.getJSONObject(i);

                                    // Extract basic product details
                                    String productId = productObj.optString("productId", "N/A");
                                    String productName = productObj.optString("productName", "N/A");
                                    String weight = productObj.optString("weight", "N/A");
                                    String karat = productObj.optString("karat", "N/A");
                                    String soilmet = productObj.getString("soulmate");

                                    // Extract imageUrls (assume first image for display)
                                    JSONArray imageUrlsArray = productObj.getJSONArray("imageUrls");
                                    String imageUrl = imageUrlsArray.length() > 0 ? imageUrlsArray.getString(0) : "";

                                    // Add product to the list
                                    pojooccusionwiseproducts.add(new POJOOCCUSIONWISEPRODUCT(productId,productName,weight,karat,imageUrl));
                                }

                                // Set the adapter
                                adpteroccusionwiseproduct = new ADPTEROCCUSIONWISEPRODUCT(pojooccusionwiseproducts,OccusionProduct.this);
                                rvexplorlist.setAdapter(adpteroccusionwiseproduct);
                            }
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley_Error", "Error in Volley request: " + error.getMessage());
                    }
                });

// Add the request to the Volley queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);

    }
}