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
import com.bansal.JewellaryApplication.Adapterclasses.AdpterCategoryGoldSublist;
import com.bansal.JewellaryApplication.pojoclasses.POJOGEThimHerProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOGetSubProduct;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetGoldSubproductActivity extends AppCompatActivity {
    TextView tvname;
    RecyclerView rvList;
    String strName;
    List<POJOGetSubProduct> pojoGetSubProducts;
    AdpterCategoryGoldSublist adpterCategoryGoldSublist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_gold_subproduct);

        getWindow().setStatusBarColor(ContextCompat.getColor(GetGoldSubproductActivity.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetGoldSubproductActivity.this, R.color.white));

        strName=getIntent().getStringExtra("name");

        tvname=findViewById(R.id.tvNameofproduct);
        rvList=findViewById(R.id.recyclerView);

        tvname.setText(strName);

        rvList.setLayoutManager(new GridLayoutManager(GetGoldSubproductActivity.this,2,GridLayoutManager.VERTICAL,false));
        pojoGetSubProducts=new ArrayList<>();

        fetchdata();
        


    }

    private void fetchdata() {
        String url ="https://api.gehnamall.com/api/getProducts?gifting=Sister";

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
                                    pojoGetSubProducts.add(new POJOGetSubProduct(productId,productName,weight,karat,imageUrl));
                                }

                                // Set the adapter
                                adpterCategoryGoldSublist = new AdpterCategoryGoldSublist(pojoGetSubProducts,GetGoldSubproductActivity.this);
                                rvList.setAdapter(adpterCategoryGoldSublist);
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