package com.bansalandsons.JewellaryApplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bansalandsons.JewellaryApplication.Adapterclasses.ADPTERGetHimORHerProduct;
import com.bansalandsons.JewellaryApplication.pojoclasses.POJOGEThimHerProduct;

import org.json.JSONArray;
import org.json.JSONException;
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
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_him_orherproduct);
        getWindow().setStatusBarColor(ContextCompat.getColor(HimORHerproduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(HimORHerproduct.this, R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        
        gender=getIntent().getStringExtra("gender");
        preferences= PreferenceManager.getDefaultSharedPreferences(HimORHerproduct.this);
        editor=preferences.edit();
        editor.putString("Himorhercategory",gender);
        editor.apply();
        
        tvhimorher=findViewById(R.id.tvHIMorHERprohimorher);
        rvProductList=findViewById(R.id.rvHimeorherproduct);

        rvProductList.setLayoutManager(new GridLayoutManager(HimORHerproduct.this,2,GridLayoutManager.VERTICAL,false));
        pojogeThimHerProducts = new ArrayList<>();
        tvhimorher.setText(gender);
        
        fetchProduct();
    }

    private void fetchProduct() {
        String url = API_URL +gender+"&wholeseller=BANSAL";

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
                                    String karat = productObj.optString("karat", "N/A");   String description = productObj.optString("description", "N/A");
                                    String soilmet = productObj.getString("soulmate");

                                    // Extract imageUrls (assume first image for display)
                                    JSONArray imageUrlsArray = productObj.getJSONArray("imageUrls");
                                    String imageUrl = imageUrlsArray.length() > 0 ? imageUrlsArray.getString(0) : "";

                                    // Add product to the list
                                    pojogeThimHerProducts.add(new POJOGEThimHerProduct(productId,productName,weight,karat,imageUrl,soilmet,description));

                                }

                                // Set up the RecyclerView adapter
                                adpterGetHimORHerProduct = new ADPTERGetHimORHerProduct(pojogeThimHerProducts,HimORHerproduct.this);
                                rvProductList.setAdapter(adpterGetHimORHerProduct);
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