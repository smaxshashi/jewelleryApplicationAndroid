package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Occusionfullproductdetails extends AppCompatActivity {

     ViewPager2 viewPager;
     TextView tvProductName, tvKaratValue, tvWeightValue, tvCompanyName,tvwashtage;
    Button btnAddToWishlist;
    String productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_occusionfullproductdetails);
        getWindow().setNavigationBarColor(ContextCompat.getColor(Occusionfullproductdetails.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(Occusionfullproductdetails.this,R.color.maroon));

        productId=getIntent().getStringExtra("productID");

        viewPager = findViewById(R.id.viewPager);
        tvProductName = findViewById(R.id.tvProductName);
        tvKaratValue = findViewById(R.id.tvKaratValue);
        tvWeightValue = findViewById(R.id.tvWeightValue);
        tvwashtage=findViewById(R.id.tvWashtageValue);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        btnAddToWishlist = findViewById(R.id.btnAddToCart);

        fetchProductDetails(productId);

    }

    private void fetchProductDetails(String productId) {
        String url = "http://3.110.34.172:8080/api/getProducts?occasion=diwali";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Parse product data
                        JSONArray productsArray = response.getJSONArray("products");
                        JSONArray imageUrlArray = response.getJSONArray("imageUrl");

                        // Find product by productId
                        JSONObject selectedProduct = null;
                        for (int i = 0; i < productsArray.length(); i++) {
                            JSONObject product = productsArray.getJSONObject(i);
                            if (product.getString("productId").equals(productId)) {
                                selectedProduct = product;
                                break;
                            }
                        }

                        // Extract the images for the selected product
                        List<String> imageUrls = new ArrayList<>();
                        for (int i = 0; i < imageUrlArray.length(); i++) {
                            JSONObject image = imageUrlArray.getJSONObject(i);
                            if (image.getString("productId").equals(productId)) {
                                imageUrls.add(image.getString("imageUrl"));
                            }
                        }

                        // Update the UI with product details
                        if (selectedProduct != null) {
                            String productName = selectedProduct.getString("productName");
                            String karatage = selectedProduct.getString("karat");
                            String weight = selectedProduct.getString("weight");
                            String wastage = selectedProduct.getString("wastage");

                            tvProductName.setText(productName);
                            tvKaratValue.setText(karatage);
                            tvWeightValue.setText(weight);
                            tvwashtage.setText(wastage);

                            // Setup Image Slider
                            setupImageSlider(imageUrls);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Occusionfullproductdetails.this, "Error parsing product data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(Occusionfullproductdetails.this, "Failed to fetch product details", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void setupImageSlider(List<String> imageUrls) {
        ImagesliderAdapter adapter = new ImagesliderAdapter(this, imageUrls);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
    }
}