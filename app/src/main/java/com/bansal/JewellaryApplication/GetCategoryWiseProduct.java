package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGETCATEGORYPRODUCT2;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGetHimORHerProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETCategoryproduct2;
import com.bansal.JewellaryApplication.pojoclasses.POJOGEThimHerProduct;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetCategoryWiseProduct extends AppCompatActivity {

 RecyclerView rvList;
 List<POJOGETCategoryproduct2> pojogetCategoryproduct2s;

 ADPTERGETCATEGORYPRODUCT2 adptergetcategoryproduct2;
    private static final String API_URL = "http://3.110.34.172:8080/api/getProducts?category=4001&subCategory=10002";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_category_wise_product); // Ensure this matches your layout XML file

        // Set status bar and navigation bar colors
        getWindow().setStatusBarColor(ContextCompat.getColor(GetCategoryWiseProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetCategoryWiseProduct.this, R.color.white));

     rvList=findViewById(R.id.rvcategoryproduct);

        rvList.setLayoutManager(new GridLayoutManager(GetCategoryWiseProduct.this,2,GridLayoutManager.VERTICAL,false));
        pojogetCategoryproduct2s = new ArrayList<>();
        
        
        fetchproductdata();

        // Get category code from Intent
    }

    private void fetchproductdata() {
        String url = API_URL;

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
                                    pojogetCategoryproduct2s.add(new POJOGETCategoryproduct2(productId,productName,weight,karat,imageUrl));
                                }

                                // Set the adapter
                                adptergetcategoryproduct2 = new ADPTERGETCATEGORYPRODUCT2(pojogetCategoryproduct2s,GetCategoryWiseProduct.this);
                                rvList.setAdapter(adptergetcategoryproduct2);
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
