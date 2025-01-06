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
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGETCATEGORYPRODUCT2;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGiftingProduct;
import com.bansal.JewellaryApplication.Adapterclasses.ADPterSubcategory;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterNoSub;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETCategoryproduct2;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETNOSUB;
import com.bansal.JewellaryApplication.pojoclasses.POJOGiftingproduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOOTHERSUBCATEGORY;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetCategoryProductSubnot extends AppCompatActivity {
    TextView tvgift;
    RecyclerView rvList;
    String gift;

    AdpterNoSub adpterNoSub;
    List<POJOGETNOSUB> pojogetnosubs;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String strCategoryname,strCategoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_category_product_subnot);
        getWindow().setStatusBarColor(ContextCompat.getColor(GetCategoryProductSubnot.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetCategoryProductSubnot.this, R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        strCategoryId=getIntent().getStringExtra("CategoryCode");
        strCategoryname=getIntent().getStringExtra("categoryname");
        SharedPreferences preferences1= PreferenceManager.getDefaultSharedPreferences(GetCategoryProductSubnot.this);
        SharedPreferences.Editor editor1=preferences1.edit();
        editor1.putString("NoCategory",strCategoryId);
        editor1.apply();

        tvgift=findViewById(R.id.tvgift);
        rvList=findViewById(R.id.rvGiftingList);
        rvList.setLayoutManager(new GridLayoutManager(GetCategoryProductSubnot.this,2,GridLayoutManager.VERTICAL,false));
        tvgift.setText(strCategoryname);
        pojogetnosubs=new ArrayList<>();

        fetchproduct();



    }

    private void fetchproduct() {
        String url = "https://api.gehnamall.com/api/getProducts?wholeseller=BANSAL&categoryCode="+strCategoryId;

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
                                    pojogetnosubs.add(new POJOGETNOSUB(productId, productName, weight, karat, imageUrl,discriptin));
                                }

                                // Set up the RecyclerView adapter
                                adpterNoSub = new AdpterNoSub(pojogetnosubs, GetCategoryProductSubnot.this);
                                rvList.setAdapter(adpterNoSub);
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