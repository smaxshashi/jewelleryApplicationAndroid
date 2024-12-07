package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.util.Log;
import android.view.PixelCopy;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGetcategorygenderproduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOgetCTaegorygenderwiseproduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetCtegoryProduct extends AppCompatActivity {

    RecyclerView rvlsitofproduct;
    String categorycode,gendercode;
    List<POJOgetCTaegorygenderwiseproduct> pojOgetCTaegorygenderwiseproducts;
    AdpterGetcategorygenderproduct adpterGetcategorygenderproduct;
    private static final String BASE_URL = "https://api.gehnamall.com/api/subCategories";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_ctegory_product);
        getWindow().setStatusBarColor(ContextCompat.getColor(GetCtegoryProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetCtegoryProduct.this, R.color.white));

        categorycode=getIntent().getStringExtra("categorycode");
        gendercode=getIntent().getStringExtra("gendercode");


        rvlsitofproduct = findViewById(R.id.recyclerViewlistofproduct);

        rvlsitofproduct.setLayoutManager(new GridLayoutManager(GetCtegoryProduct.this,1,GridLayoutManager.VERTICAL,false));
        pojOgetCTaegorygenderwiseproducts = new ArrayList<>();




        getProduct();



    }

    private void getProduct() {
        String categoryCode = "4002";  // Example category code
        String genderCode = "2";       // Example gender filter
        String API_URL = "https://api.gehnamall.com/api/subCategories/" + categoryCode + "?gender=" + genderCode;

        Log.d("API_URL", "Request URL: " + API_URL);  // Log the URL being used

        RequestQueue requestQueue = Volley.newRequestQueue(GetCtegoryProduct.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, API_URL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("API_RESPONSE", "Response: " + response);
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String strid = jsonObject.getString("subcategoryId");
                                String strname = jsonObject.getString("subcategoryName");
                                String strimage = jsonObject.optString("exfield1", "default_image_url");

                                pojOgetCTaegorygenderwiseproducts.add(new POJOgetCTaegorygenderwiseproduct(strid, strname, strimage));
                            }

                            // Update RecyclerView
                            adpterGetcategorygenderproduct = new AdpterGetcategorygenderproduct(pojOgetCTaegorygenderwiseproducts, GetCtegoryProduct.this);
                            rvlsitofproduct.setAdapter(adpterGetcategorygenderproduct);
                        } catch (JSONException e) {
                            Log.e("JSON_ERROR", "Error parsing response: " + e.getMessage());
                            Toast.makeText(GetCtegoryProduct.this, "Error parsing data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API_ERROR", "Error fetching data: " + error.toString());
                        Toast.makeText(GetCtegoryProduct.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }




}