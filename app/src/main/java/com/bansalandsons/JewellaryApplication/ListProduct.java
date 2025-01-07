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
import com.bansalandsons.JewellaryApplication.Adapterclasses.AdpterListUp;
import com.bansalandsons.JewellaryApplication.pojoclasses.POjoupperlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListProduct extends AppCompatActivity {
    TextView tvname;
    RecyclerView rvlist;
    String categorycode,name;
    List<POjoupperlist> pOjoupperlistList;
    AdpterListUp adpterListUp;

    String api="https://api.gehnamall.com/api/getProducts?wholeseller=test&gifting=sister";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_product);
        getWindow().setNavigationBarColor(ContextCompat.getColor(ListProduct.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(ListProduct.this,R.color.maroon));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        categorycode=getIntent().getStringExtra("categorycode");
        name=getIntent().getStringExtra("name");
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(ListProduct.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("Lightcategorycode",categorycode);
        editor.apply();


        tvname=findViewById(R.id.tvlightname);rvlist=findViewById(R.id.rvGiftingList);



        tvname.setText(name);
        rvlist.setLayoutManager(new GridLayoutManager(ListProduct.this,2,GridLayoutManager.VERTICAL,false));
        pOjoupperlistList=new ArrayList<>();
        fetchproduct();

    }

    private void fetchproduct() {
        String url="https://api.gehnamall.com/api/getProducts?categoryCode="+categorycode+"&lightWeight=light&wholeseller=Bansal";
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
                                    String description = productObj.optString("description", "N/A");
                                    String soilmet = productObj.getString("soulmate");

                                    // Extract imageUrls (assume first image for display)
                                    JSONArray imageUrlsArray = productObj.getJSONArray("imageUrls");
                                    String imageUrl = imageUrlsArray.length() > 0 ? imageUrlsArray.getString(0) : "";

                                    // Add product to the list
                                    pOjoupperlistList.add(new POjoupperlist(productId,productName,weight,karat,imageUrl,description));
                                }

                                // Set the adapter
                                adpterListUp = new AdpterListUp(pOjoupperlistList,ListProduct.this);
                                rvlist.setAdapter(adpterListUp);
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