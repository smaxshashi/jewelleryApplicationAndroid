package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HimORHerproduct extends AppCompatActivity {
    
    TextView tvhimorher;
    RecyclerView rvProductList;
    String gender;
    List<POJOGEThimHerProduct> pojogeThimHerProducts;
    ADPTERGetHimORHerProduct adpterGetHimORHerProduct;

    private static final String API_URL = "https://api.gehnamall.com/api/getProducts?soulmate=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_him_orherproduct);
        getWindow().setStatusBarColor(ContextCompat.getColor(HimORHerproduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(HimORHerproduct.this, R.color.white));
        
        gender=getIntent().getStringExtra("gender");
        
        tvhimorher=findViewById(R.id.tvHIMorHERprohimorher);
        rvProductList=findViewById(R.id.rvHimeorherproduct);

        rvProductList.setLayoutManager(new GridLayoutManager(HimORHerproduct.this,2,GridLayoutManager.VERTICAL,false));
        pojogeThimHerProducts = new ArrayList<>();
        tvhimorher.setText(gender);
        
        fetchProduct();
    }

    private void fetchProduct() {
        String url = API_URL +gender+"&wholeseller=test";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 0) {
                                // Parse the product data
                                JSONArray productsArray = response.getJSONArray("products");
                                JSONArray imageUrlsArray = response.getJSONArray("imageUrl");

                                for (int i = 0; i < productsArray.length(); i++) {
                                    JSONObject productObj = productsArray.getJSONObject(i);

                                    String productId = productObj.getString("productId");
                                    String productName = productObj.getString("productName");
                                    String weight = productObj.getString("weight");
                                    String karat = productObj.getString("karat");
                                    String soilmet=productObj.getString("soulmate");

                                    // Fetch the image URL (assuming the first image is related to the product)
                                    String imageUrl = imageUrlsArray.getJSONObject(i).getString("imageUrl");

                                    // Create a Product object and add it to the list
                                    pojogeThimHerProducts.add(new POJOGEThimHerProduct(productId,productName,weight,karat,imageUrl,soilmet));
                                }

                                // Set the adapter
                                adpterGetHimORHerProduct = new ADPTERGetHimORHerProduct(pojogeThimHerProducts,HimORHerproduct.this);
                                rvProductList.setAdapter(adpterGetHimORHerProduct);
                            }
                        } catch (Exception e) {
                            Log.e("Error", "Error parsing data: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", "Error in Volley request: " + error.getMessage());
                    }
                });

        // Add the request to the Volley queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);


    }
}