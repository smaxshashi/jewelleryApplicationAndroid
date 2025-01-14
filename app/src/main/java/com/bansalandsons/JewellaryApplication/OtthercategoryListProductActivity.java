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
import com.bansalandsons.JewellaryApplication.Adapterclasses.AdpterOtehrCategory;
import com.bansalandsons.JewellaryApplication.pojoclasses.PojoOtehrcategory;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OtthercategoryListProductActivity extends AppCompatActivity {
    TextView tvname;
    RecyclerView rvlidt;
    String strName,strcategorycode,strsubcategorycode;
    List<PojoOtehrcategory> pojoOtehrcategoryList;
    AdpterOtehrCategory adpterOtehrCategory;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_otthercategory_list_product);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        getWindow().setStatusBarColor(ContextCompat.getColor(OtthercategoryListProductActivity.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(OtthercategoryListProductActivity.this, R.color.white));
        strName=getIntent().getStringExtra("subcategory");
        strsubcategorycode=getIntent().getStringExtra("subcategorycode");
        preferences= PreferenceManager.getDefaultSharedPreferences(OtthercategoryListProductActivity.this);
        strcategorycode=preferences.getString("Othercategorycode","");
        SharedPreferences preferences1=PreferenceManager.getDefaultSharedPreferences(OtthercategoryListProductActivity.this);
        SharedPreferences.Editor editor1=preferences1.edit();
        editor1.putString("othersubcode",strsubcategorycode);
        editor1.apply();




        tvname=findViewById(R.id.tvcategory);
        rvlidt=findViewById(R.id.rcvlistdimamond);
        tvname.setText(strName);
        rvlidt.setLayoutManager(new GridLayoutManager(OtthercategoryListProductActivity.this, 2, GridLayoutManager.VERTICAL, false));
        pojoOtehrcategoryList = new ArrayList<>();
        getproduct();


    }

    private void getproduct() {
        String url ="https://api.gehnamall.com/api/getProducts?wholeseller=BANSAL&categoryCode="+strcategorycode+"&subCategoryCode="+strsubcategorycode;
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
                                    String karat = productObj.getString("karat"); String description = productObj.getString("description");

                                    // Fetch the image URL (assuming the first image is related to the product)
                                    JSONArray imageUrlsArray = productObj.getJSONArray("imageUrls");
                                    String imageUrl = imageUrlsArray.length() > 0 ? imageUrlsArray.getString(0) : "";

                                    // Create a Product object and add it to the list
                                    pojoOtehrcategoryList.add(new PojoOtehrcategory(productId,productName,weight,karat,imageUrl,description));
                                }

                                // Set the adapter

                                adpterOtehrCategory = new AdpterOtehrCategory(pojoOtehrcategoryList,OtthercategoryListProductActivity.this);
                                rvlidt.setAdapter(adpterOtehrCategory);
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