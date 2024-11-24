package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERDIMANDPRODUCTLIST;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGOLDMENDATA;
import com.bansal.JewellaryApplication.pojoclasses.POJODIMAONDPRODUCTLIST;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETDATAMENGOLD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DiamondProductLIst extends AppCompatActivity {
    String gc, cc;
    List<POJODIMAONDPRODUCTLIST> pojodimaondproductlists;
   ADPTERDIMANDPRODUCTLIST adpterdimandproductlist;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diamond_product_list);
        getWindow().setStatusBarColor(ContextCompat.getColor(DiamondProductLIst.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(DiamondProductLIst.this, R.color.white));

        // Get Intent data
        cc = getIntent().getStringExtra("categorycode");
        gc = getIntent().getStringExtra("gendercode");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        // Use GridLayoutManager for a 2-column grid

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 2 columns
        pojodimaondproductlists = new ArrayList<>();
       adpterdimandproductlist = new ADPTERDIMANDPRODUCTLIST(pojodimaondproductlists,DiamondProductLIst.this);
        recyclerView.setAdapter(adpterdimandproductlist);

        GetProductList();



    }

    private void GetProductList() {
        String url = "http://3.110.34.172:8080/api/subCategories/" + cc + "?genderCode=" + gc;
        Log.d("API URL", url);


        RequestQueue requestQueue = Volley.newRequestQueue(DiamondProductLIst.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            pojodimaondproductlists.clear();
                            Log.d("API Response", response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObj = response.getJSONObject(i);
                                Log.d("Item " + i, categoryObj.toString());

                                String name = categoryObj.getString("subcategoryName");
                                String code = categoryObj.getString("subcategoryCode");

                                pojodimaondproductlists.add(new POJODIMAONDPRODUCTLIST(name,code));
                            }
                            Log.d("Data List Size", String.valueOf(pojodimaondproductlists.size()));

                            // Update RecyclerView with the new data
                            adpterdimandproductlist.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DiamondProductLIst.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handling network error
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(DiamondProductLIst.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);

    }
}