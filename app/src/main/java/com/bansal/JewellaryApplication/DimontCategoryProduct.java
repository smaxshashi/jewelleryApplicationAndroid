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
import com.bansal.JewellaryApplication.Adapterclasses.AdpterCategoryGoldSublist;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterDiamonSubProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJODiamondSubProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOGetSubProduct;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DimontCategoryProduct extends AppCompatActivity {
    TextView tvname;
    RecyclerView rvlidt;
    List<POJODiamondSubProduct> pojoDiamondSubProducts;
    AdpterDiamonSubProduct adpterDiamonSubProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dimont_category_product);
        getWindow().setStatusBarColor(ContextCompat.getColor(DimontCategoryProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(DimontCategoryProduct.this, R.color.white));
        String name;
        name=getIntent().getStringExtra("Categoryname");

        tvname=findViewById(R.id.tvcategory);
        rvlidt=findViewById(R.id.rcvlistdimamond);
        tvname.setText(name);
        rvlidt.setLayoutManager(new GridLayoutManager(DimontCategoryProduct.this,2,GridLayoutManager.VERTICAL,false));
        pojoDiamondSubProducts=new ArrayList<>();

        fetchdata();

    }

    private void fetchdata() {
        String url ="http://3.110.34.172:8080/api/getProducts?gifting=Sister";

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

                                    int productId = productObj.getInt("productId");
                                    String productName = productObj.getString("productName");
                                    String weight = productObj.getString("weight");
                                    String karat = productObj.getString("karat");

                                    // Fetch the image URL (assuming the first image is related to the product)
                                    String imageUrl = imageUrlsArray.getJSONObject(i).getString("imageUrl");

                                    // Create a Product object and add it to the list
                                    pojoDiamondSubProducts.add(new POJODiamondSubProduct(productId,productName,weight,karat,imageUrl));
                                }

                                // Set the adapter
                                adpterDiamonSubProduct = new AdpterDiamonSubProduct(pojoDiamondSubProducts,DimontCategoryProduct.this);
                                rvlidt.setAdapter(adpterDiamonSubProduct);
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