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
import com.bansal.JewellaryApplication.Adapterclasses.AdpterCategoryGoldSublist;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterDiamonSubProduct;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterDiamondSubcategoryProduct;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGoldproductList;
import com.bansal.JewellaryApplication.pojoclasses.POJODiamondSubProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOGetSubProduct;
import com.bansal.JewellaryApplication.pojoclasses.PojoDiamondProductSubcategorylist;
import com.bansal.JewellaryApplication.pojoclasses.PojoGoldProduct;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DimontCategoryProduct extends AppCompatActivity {
    TextView tvname;
    RecyclerView rvlidt;
    String strName,strcategorycode,strsubcategorycode,strGendercode;
    List<PojoDiamondProductSubcategorylist> pojoDiamondSubProducts;
    AdpterDiamondSubcategoryProduct adpterDiamondSubcategoryProduct;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dimont_category_product);
        getWindow().setStatusBarColor(ContextCompat.getColor(DimontCategoryProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(DimontCategoryProduct.this, R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);





        strName=getIntent().getStringExtra("Categoryname");
        strsubcategorycode=getIntent().getStringExtra("subcategorycode");
        preferences= PreferenceManager.getDefaultSharedPreferences(DimontCategoryProduct.this);
        strcategorycode=preferences.getString("Dimondtcategorycode","");
        strGendercode=preferences.getString("diamondgendercode","");

        SharedPreferences preferences1= PreferenceManager.getDefaultSharedPreferences(DimontCategoryProduct.this);
        SharedPreferences.Editor editor=preferences1.edit();
        editor.putString("subcategorycodedi",strsubcategorycode);
        editor.apply();



        tvname = findViewById(R.id.tvcategory);
        rvlidt = findViewById(R.id.rcvlistdimamond);
        tvname.setText(strName);
        rvlidt.setLayoutManager(new GridLayoutManager(DimontCategoryProduct.this, 2, GridLayoutManager.VERTICAL, false));
        pojoDiamondSubProducts = new ArrayList<>();
        getproduct();




    }

    private void getproduct() {
        String url ="https://api.gehnamall.com/api/getProducts?wholeseller=BANSAL&categoryCode="+strcategorycode+"&subCategoryCode="+strsubcategorycode+"&genderCode="+strGendercode;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 0) { // Check status = 0
                                JSONArray productsArray = response.getJSONArray("products");

                                for (int i = 0; i < productsArray.length(); i++) {
                                    JSONObject productObj = productsArray.getJSONObject(i);

                                    String productId = productObj.getString("productId");
                                    String productName = productObj.getString("productName");
                                    String weight = productObj.getString("weight");
                                    String karat = productObj.getString("karat");

                                    // Fetch the image URL (assuming the first image is related to the product)
                                    // Fetch the image URL (assuming the first image is related to the product)
                                    JSONArray imageUrlsArray = productObj.getJSONArray("imageUrls");
                                    String imageUrl = imageUrlsArray.length() > 0 ? imageUrlsArray.getString(0) : "";

                                    // Create a Product object and add it to the list
                                    pojoDiamondSubProducts.add(new PojoDiamondProductSubcategorylist(productId,productName,weight,karat,imageUrl));
                                }

                                // Set the adapter

                                adpterDiamondSubcategoryProduct = new AdpterDiamondSubcategoryProduct(pojoDiamondSubProducts,DimontCategoryProduct.this);
                                rvlidt.setAdapter(adpterDiamondSubcategoryProduct);
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