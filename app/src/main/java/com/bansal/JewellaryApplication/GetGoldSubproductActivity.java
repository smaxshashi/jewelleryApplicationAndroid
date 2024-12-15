package com.bansal.JewellaryApplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGiftingProduct;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterCategoryGoldSublist;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGoldproductList;
import com.bansal.JewellaryApplication.pojoclasses.POJOGEThimHerProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOGetSubProduct;
import com.bansal.JewellaryApplication.pojoclasses.POJOGiftingproduct;
import com.bansal.JewellaryApplication.pojoclasses.PojoGoldProduct;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetGoldSubproductActivity extends AppCompatActivity {
    TextView tvname;
    RecyclerView rvList;
    String strName,strcategorycode,strsubcategorycode,strGendercode;
   List<PojoGoldProduct> pojogetProduct;
    AdpterGoldproductList adpterGiftingProduct;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_get_gold_subproduct);

        getWindow().setStatusBarColor(ContextCompat.getColor(GetGoldSubproductActivity.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetGoldSubproductActivity.this, R.color.white));




        strName=getIntent().getStringExtra("Subcategoryname");
        strsubcategorycode=getIntent().getStringExtra("subcategorycode");
        preferences= PreferenceManager.getDefaultSharedPreferences(GetGoldSubproductActivity.this);
        strcategorycode=preferences.getString("Goldcategorycode","");
        strGendercode=preferences.getString("gendercodegold","");

        tvname=findViewById(R.id.tvNameofproduct);
        rvList=findViewById(R.id.recyclerView);

        tvname.setText(strName);



        rvList.setLayoutManager(new GridLayoutManager(GetGoldSubproductActivity.this,2,GridLayoutManager.VERTICAL,false));
        pojogetProduct=new ArrayList<>();

        getproduct();


        


    }

    private void getproduct() {
        String url ="https://api.gehnamall.com/api/getProducts?wholeseller=BANSAL&categoryCode="+strcategorycode+"&subCategoryCode="+strsubcategorycode+"&genderCode="+strGendercode;
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

                                    String productId = productObj.getString("productId");
                                    String productName = productObj.getString("productName");
                                    String weight = productObj.getString("weight");
                                    String karat = productObj.getString("karat");

                                    // Fetch the image URL (assuming the first image is related to the product)
                                    String imageUrl = imageUrlsArray.getJSONObject(i).getString("imageUrl");

                                    // Create a Product object and add it to the list
                                    pojogetProduct.add(new PojoGoldProduct(productId,productName,weight,karat,imageUrl));
                                }

                                // Set the adapter

                                adpterGiftingProduct = new AdpterGoldproductList(pojogetProduct,GetGoldSubproductActivity.this);
                                rvList.setAdapter(adpterGiftingProduct);
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