package com.bansal.JewellaryApplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGetMenWomenProductlist;
import com.bansal.JewellaryApplication.pojoclasses.POJOMenWomenProductlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductList extends AppCompatActivity {
    RecyclerView rvlistofproduct;
    TextView tvcode;
    String categorycode, gendercode;
    List<POJOMenWomenProductlist> pojoMenWomenProductlists;
    AdpterGetMenWomenProductlist adpterGetMenWomenProductlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Set status bar and navigation bar colors
        getWindow().setStatusBarColor(ContextCompat.getColor(ProductList.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(ProductList.this, R.color.white));

        // Retrieve category and gender codes from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        categorycode = sharedPreferences.getString("CategoryCode", "4001");
        gendercode = sharedPreferences.getString("GenderCode", "1");

        // Initialize RecyclerView and product list
        rvlistofproduct = findViewById(R.id.recyclerViewProductlistofproduct);
        tvcode=findViewById(R.id.svProductlistsearchViewproduct);
        tvcode.setText(categorycode);
        rvlistofproduct.setLayoutManager(new GridLayoutManager(ProductList.this, 1));
        pojoMenWomenProductlists = new ArrayList<>();



        // Log category and gender codes for debugging
        Log.d("ProductList", "CategoryCode: " + categorycode + ", GenderCode: " + gendercode);

        // Fetch product data
        getProduct(categorycode, gendercode);
    }

    private void getProduct(String categorycode, String gendercode) {
        // Validate inputs
        if (categorycode.isEmpty() || gendercode.isEmpty()) {
            Log.e("API_Error", "CategoryCode or GenderCode is missing!");
            Toast.makeText(ProductList.this, "Invalid Category or Gender Code", Toast.LENGTH_SHORT).show();
            return;
        }

        // Construct the API URL
        String apiUrl = "http://3.110.34.172:8080/api/subCategories/4001?genderCode=1";
        Log.d("API_URL", apiUrl); // Log the API URL

        // Create a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(ProductList.this);

        // Create a new JsonObjectRequest
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Parse the response
                            JSONArray subCategories = response.optJSONArray("subCategories");
                            if (subCategories != null) {
                                for (int i = 0; i < subCategories.length(); i++) {
                                    JSONObject productObj = subCategories.getJSONObject(i);

                                    // Extract product details
                                    String productName = productObj.optString("subcategoryName", "N/A");
                                    String productImage = productObj.optString("exfield1", "");

                                    // Add product to the list
                                    pojoMenWomenProductlists.add(new POJOMenWomenProductlist(productName, productImage));
                                }
                                // Log the number of products fetched
                                Log.d("ProductList", "Total products fetched: " + pojoMenWomenProductlists.size());

                                // Set the adapter
                                adpterGetMenWomenProductlist = new AdpterGetMenWomenProductlist(pojoMenWomenProductlists, ProductList.this);
                                rvlistofproduct.setAdapter(adpterGetMenWomenProductlist);
                            } else {
                                Log.e("API_Error", "No subcategories found in response");
                                Toast.makeText(ProductList.this, "No products available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductList.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log detailed error information
                        if (error.networkResponse != null) {
                            Log.e("API_Error", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("API_Error", "Response Data: " + new String(error.networkResponse.data));
                        } else if (error.getCause() != null) {
                            Log.e("API_Error", "Cause: " + error.getCause());
                        } else {
                            Log.e("API_Error", "Message: " + error.getMessage());
                        }
                        Toast.makeText(ProductList.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the queue
        requestQueue.add(request);
    }
}

