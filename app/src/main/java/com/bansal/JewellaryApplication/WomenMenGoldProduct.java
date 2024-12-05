package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.ADPTERGOLDMENDATA;
import com.bansal.JewellaryApplication.pojoclasses.POJOGETDATAMENGOLD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class WomenMenGoldProduct extends AppCompatActivity {

    String gc, cc;
    List<POJOGETDATAMENGOLD> pojogetdatamengolds;
    ADPTERGOLDMENDATA adptergoldmendata;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        // Set status bar and navigation bar colors
        getWindow().setStatusBarColor(ContextCompat.getColor(WomenMenGoldProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(WomenMenGoldProduct.this, R.color.white));

        // Get Intent data
        cc = getIntent().getStringExtra("categorycode");
        gc = getIntent().getStringExtra("gendercode");

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        // Use GridLayoutManager for a 2-column grid

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1)); // 2 columns
        pojogetdatamengolds = new ArrayList<>();
        adptergoldmendata = new ADPTERGOLDMENDATA(pojogetdatamengolds, WomenMenGoldProduct.this);
        recyclerView.setAdapter(adptergoldmendata);



        // Fetch data
        GoldMenData();
    }

    private void GoldMenData() {
        // Dynamically construct the URL
        String url = "http://3.110.34.172:8080/api/subCategories/" + cc + "?genderCode=" + gc+"&wholeseller=BANSAL";
        Log.d("API URL", url);


        RequestQueue requestQueue = Volley.newRequestQueue(WomenMenGoldProduct.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            pojogetdatamengolds.clear();
                            Log.d("API Response", response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObj = response.getJSONObject(i);
                                Log.d("Item " + i, categoryObj.toString());

                                String name = categoryObj.getString("subcategoryName");
                                String code = categoryObj.getString("subcategoryCode");
                                String image = categoryObj.getString("exfield1");

                                pojogetdatamengolds.add(new POJOGETDATAMENGOLD(name, code,image));
                            }
                            Log.d("Data List Size", String.valueOf(pojogetdatamengolds.size()));

                            // Update RecyclerView with the new data
                            adptergoldmendata.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(WomenMenGoldProduct.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handling network error
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(WomenMenGoldProduct.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}

