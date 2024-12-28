package com.bansal.JewellaryApplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGetHimORHerProduct;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGiftingProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOGEThimHerProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOGiftingproduct;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class GiftingProduct extends AppCompatActivity {

    TextView tvgift;
    RecyclerView rvList;
    String gift;
    private static final String API_URL = "https://api.gehnamall.com/api/getProducts?wholeseller=BANSAL&gifting=";
    List<POJOGiftingproduct> pojoGiftingproducts;
    ADPTERGiftingProduct adpterGiftingProduct;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gifting_product);
        getWindow().setStatusBarColor(ContextCompat.getColor(GiftingProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GiftingProduct.this, R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        gift=getIntent().getStringExtra("gift");
        preferences= PreferenceManager.getDefaultSharedPreferences(GiftingProduct.this);
        editor=preferences.edit();
        editor.putString("Giftingcategory",gift);
        editor.apply();

        tvgift = findViewById(R.id.tvgift);
        rvList=findViewById(R.id.rvGiftingList);
        rvList.setLayoutManager(new GridLayoutManager(GiftingProduct.this,2,GridLayoutManager.VERTICAL,false));
        tvgift.setText(gift);
        pojoGiftingproducts = new ArrayList<>();
        
        fetchproduct();
        

    }

    private void fetchproduct() {
        String url = "https://api.gehnamall.com/api/getProducts?wholeseller=BANSAL&gifting="+gift;

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
                                    String discriptin = productObj.optString("description", "N/A");

                                    // Extract imageUrls (assume first image for display)
                                    JSONArray imageUrlsArray = productObj.getJSONArray("imageUrls");
                                    String imageUrl = imageUrlsArray.length() > 0 ? imageUrlsArray.getString(0) : "";

                                    // Add product to the list
                                    pojoGiftingproducts.add(new POJOGiftingproduct(productId, productName, weight, karat, imageUrl,discriptin));
                                }

                                // Set up the RecyclerView adapter
                                adpterGiftingProduct = new ADPTERGiftingProduct(pojoGiftingproducts, GiftingProduct.this);
                                rvList.setAdapter(adpterGiftingProduct);
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