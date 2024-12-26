package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGETCATEGORYPRODUCT2;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGetHimORHerProduct;
import com.bansal.JewellaryApplication.Adapterclasses.ADPterSubcategory;
import com.bansal.JewellaryApplication.pojoclasses.POJODIMAONDPRODUCTLIST;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETCategoryproduct2;
import com.bansal.JewellaryApplication.pojoclasses.POJOGEThimHerProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOOTHERSUBCATEGORY;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

public class GetCategoryWiseProduct extends AppCompatActivity {

 RecyclerView rvList;
 List<POJOGETCategoryproduct2> pojogetCategoryproduct2s;
 TextView tvcategory;
 String  categoryname,categorycode;
 List<POJOOTHERSUBCATEGORY> pojoothersubcategories;
 ADPterSubcategory adPterSubcategory;

 ADPTERGETCATEGORYPRODUCT2 adptergetcategoryproduct2;
 SharedPreferences preferences;
 SharedPreferences.Editor editor;
    private static final String API_URL = "https://api.gehnamall.com/api/subCategories/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_category_wise_product); // Ensure this matches your layout XML file
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Set status bar and navigation bar colors
        getWindow().setStatusBarColor(ContextCompat.getColor(GetCategoryWiseProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetCategoryWiseProduct.this, R.color.white));
        categoryname=getIntent().getStringExtra("categoryname");
        categorycode=getIntent().getStringExtra("CategoryCode");
        preferences= PreferenceManager.getDefaultSharedPreferences(GetCategoryWiseProduct.this);
        editor=preferences.edit();
        editor.putString("Othercategorycode",categorycode);editor.apply();

     rvList=findViewById(R.id.rvcategoryproduct);
     tvcategory=findViewById(R.id.tvcategory);

     tvcategory.setText(categoryname);

        rvList.setLayoutManager(new GridLayoutManager(this, 1));
        pojoothersubcategories = new ArrayList<>();
        adPterSubcategory = new ADPterSubcategory(pojoothersubcategories,GetCategoryWiseProduct.this);
        rvList.setAdapter(adPterSubcategory);
        
        
        fetchproductdata();

        // Get category code from Intent
    }

    private void fetchproductdata() {
        String url = API_URL+categorycode+"?wholeseller=BANSAL";
        Log.d("API URL", url);


        RequestQueue requestQueue = Volley.newRequestQueue(GetCategoryWiseProduct.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            pojoothersubcategories.clear();
                            Log.d("API Response", response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObj = response.getJSONObject(i);
                                Log.d("Item " + i, categoryObj.toString());

                                String name = categoryObj.getString("subcategoryName");
                                String code = categoryObj.getString("subcategoryCode");
                                String image=categoryObj.getString("exfield1");
                                pojoothersubcategories.add(new POJOOTHERSUBCATEGORY(name,code,image));


                            }
                            Log.d("Data List Size", String.valueOf(pojoothersubcategories.size()));

                            // Update RecyclerView with the new data
                            adPterSubcategory.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(GetCategoryWiseProduct.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handling network error
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(GetCategoryWiseProduct.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);

    }



    }

