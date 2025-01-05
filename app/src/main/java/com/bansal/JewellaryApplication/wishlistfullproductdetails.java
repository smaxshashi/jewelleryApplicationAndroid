package com.bansal.JewellaryApplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.Adpterimagewishlist;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class wishlistfullproductdetails extends AppCompatActivity {
    ViewPager2 viewPager;
    TextView tvProductName, tvKaratValue, tvWeightValue, tvCompanyName,tvwashtage;
    Button btnAddToWishlist;
    String productId,gifting;
    SharedPreferences preferences;
    LinearLayout ivwhtasapp,llCall;
    TabLayout tabLayout;
    String productid,UserId;
    String fetchproductid;
    TextView tvdis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_wishlistfullproductdetails);
        getWindow().setNavigationBarColor(ContextCompat.getColor(wishlistfullproductdetails.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(wishlistfullproductdetails.this,R.color.maroon));
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        UserId=sharedPreferences.getString("userId","");
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


productid=getIntent().getStringExtra("productid");



        viewPager = findViewById(R.id.viewPager);
        tvProductName = findViewById(R.id.tvProductName);
        tvKaratValue = findViewById(R.id.tvKaratValue);
        tvWeightValue = findViewById(R.id.tvWeightValue);
        tvwashtage=findViewById(R.id.tvMakingChargeValue);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        btnAddToWishlist = findViewById(R.id.btnAddToCart);
        ivwhtasapp=findViewById(R.id.llWhatsapp);tvdis=findViewById(R.id.tvdis);


        tabLayout = findViewById(R.id.tabLayout);
        llCall = findViewById(R.id.llCall);
        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer("+9191982031621"); // Replace with your phone number
            }
        });
        btnAddToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromCart(productid,UserId);
            }
        });


        ivwhtasapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "91982031621"; // Use country code without "+" prefix
                String message = "Hello, I have a question regarding the product from your app.";

                try {
                    // Ensure the URL is properly encoded
                    String url = "https://wa.me/" + phoneNumber + "?text=" + Uri.encode(message);
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                    whatsappIntent.setData(Uri.parse(url));
                    whatsappIntent.setPackage("com.whatsapp"); // Explicitly set WhatsApp package

                    v.getContext().startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    // Handle case where WhatsApp is not installed
                    Toast.makeText(v.getContext(), "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Handle generic exceptions
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        fetchProductDetails(productid);

    }

    private void removeFromCart(String productid, String userId) {
        String url = "https://api.gehnamall.com/api/removeFromCart?userId=" + userId + "&productId=" + fetchproductid;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                response -> {
                    try {
                        if (response.getInt("status") == 0) { // Assuming status 0 indicates success
                            Toast.makeText(this, "Product removed from cart", Toast.LENGTH_SHORT).show();
                            // Update UI or refresh cart
                        } else {
                            String message = response.optString("message", "Failed to remove product");
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error parsing response", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("API_ERROR", error.toString());
                    Toast.makeText(this, "Failed to remove product", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void fetchProductDetails(String productId) {
        String url = "https://api.gehnamall.com/api/getCartItem?userId="+UserId; // Adjust userId if needed

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Log.d("API_RESPONSE", response.toString());

                        // Check if 'enhancedProducts' array exists
                        if (!response.has("enhancedProducts")) {
                            Toast.makeText(this, "Invalid response format", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONArray enhancedProductsArray = response.getJSONArray("enhancedProducts");
                        JSONArray imageUrlArray = null;

                        // Handle the case when productId is null or empty
                        int parsedProductId = productId != null ? Integer.parseInt(productId) : -1;

                        // Assuming there's a single product in the response
                        JSONObject selectedProduct = null;

                        for (int i = 0; i < enhancedProductsArray.length(); i++) {
                            JSONObject product = enhancedProductsArray.getJSONObject(i);
                            if (product.optInt("productId", -1) == parsedProductId) {
                                selectedProduct = product;
                                imageUrlArray = selectedProduct.getJSONArray("imageUrls");
                                break;
                            }
                        }

                        if (selectedProduct != null) {
                            String productName = selectedProduct.optString("productName", "N/A");
                            fetchproductid = selectedProduct.optString("productId", "N/A");
                            String karat = selectedProduct.optString("karat", "N/A");
                            String weight = selectedProduct.optString("weight", "N/A");
                            String makingCharge = selectedProduct.optString("wastage", "0"); // Default to "0" or a fallback value
                            String Discription = selectedProduct.optString("description", "0"); // Default value

                            tvProductName.setText(productName);
                            tvKaratValue.setText(karat);
                            tvWeightValue.setText(weight);
                            tvwashtage.setText(makingCharge);
                            tvdis.setText(Discription);

                            // Extract and set image URLs
                            List<String> imageUrls = new ArrayList<>();
                            for (int i = 0; i < imageUrlArray.length(); i++) {
                                String imageUrl = imageUrlArray.getString(i);
                                imageUrls.add(imageUrl);
                            }

                            setupImageSlider(imageUrls);
                        } else {
                            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(this, "Error parsing product data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("API_ERROR", error.toString());
                    Toast.makeText(this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }


    private void setupImageSlider(List<String> imageUrls) {
        Adpterimagewishlist adapter = new Adpterimagewishlist(this, imageUrls);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Dots for the indicator
        }).attach();
    }






























    private void openDialer(String s) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + s));
        startActivity(intent);
    }
}