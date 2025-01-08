package com.bansalandsons.JewellaryApplication;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bansalandsons.JewellaryApplication.Adapterclasses.GiftingSlider;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GiftingFulllDetails extends AppCompatActivity {
    ViewPager2 viewPager;
    TextView tvProductName, tvKaratValue, tvWeightValue, tvCompanyName,tvwashtage;
    Button btnAddToWishlist;
    String gifting;
    SharedPreferences preferences;
    LinearLayout ivwhtasapp,llCall;
     TabLayout tabLayout;
     String impproductid,UserId,productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gifting_fulll_details);
        getWindow().setNavigationBarColor(ContextCompat.getColor(GiftingFulllDetails.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(GiftingFulllDetails.this,R.color.maroon));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        productId=getIntent().getStringExtra("giftingproductid");
        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
       // gifting=preferences.getString("Gifting","");


        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        UserId=sharedPreferences.getString("userId","");

        SharedPreferences preferences1=PreferenceManager.getDefaultSharedPreferences(GiftingFulllDetails.this);
        gifting=preferences1.getString("Giftingcategory","");

       // productId=preferences2.getString("ProductId","");
        viewPager = findViewById(R.id.viewPager);
        tvProductName = findViewById(R.id.tvProductName);
        tvKaratValue = findViewById(R.id.tvKaratValue);
        tvWeightValue = findViewById(R.id.tvWeightValue);
        tvwashtage=findViewById(R.id.tvMakingChargeValue);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        btnAddToWishlist = findViewById(R.id.btnAddToCart);
        ivwhtasapp=findViewById(R.id.llWhatsapp);
        tabLayout = findViewById(R.id.tabLayout);
        llCall = findViewById(R.id.llCall);
        tvProductName.setText(impproductid);
        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer("+9179820316211"); // Replace with your phone number
            }
        });

        ivwhtasapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "79820316211"; // Use country code without "+" prefix
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


        Fetchdata(productId);

        btnAddToWishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (isLoggedIn) {
                    // Proceed with adding to cart
                    addToCart(productId);
                } else {
                    // Show login required dialog
                    showLoginAlertDialog();
                }

            }
        });



    }

    private void addToCart(String productId) {

            String url = "https://api.gehnamall.com/api/addToCart?userId=" + UserId + "&productId=" + productId;

            RequestQueue requestQueue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String status = jsonResponse.optString("status");
                            String message = jsonResponse.optString("message");

                            if ("0".equals(status)) {
                                // Successfully added to cart
                                Toast.makeText(this, "Product added to cart successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                // Failure case
                                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Response Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> {
                        Toast.makeText(this, "Network Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    });

            requestQueue.add(stringRequest);
        }



    private void Fetchdata(String productId) {
        if (productId == null || productId.isEmpty()) {
            Log.e("Validation Error", "Product ID is null or empty");
            Toast.makeText(this, "Invalid Product ID", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://api.gehnamall.com/api/getProducts?wholeseller=BANSAL&gifting=" + gifting;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Log.d("API_RESPONSE", response.toString());

                        if (!response.has("products") || response.isNull("products")) {
                            Toast.makeText(this, "No products available", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        JSONArray productsArray = response.getJSONArray("products");
                        JSONArray imageUrlArray = null;
                        JSONObject selectedProduct = null;

                        for (int i = 0; i < productsArray.length(); i++) {
                            JSONObject product = productsArray.getJSONObject(i);
                            if (product.optInt("productId", -1) == Integer.parseInt(productId)) {
                                selectedProduct = product;
                                imageUrlArray = selectedProduct.optJSONArray("imageUrls");
                                break;
                            }
                        }

                        if (selectedProduct != null) {
                            String productName = selectedProduct.optString("productName", "N/A");
                            String karat = selectedProduct.optString("karat", "N/A");
                            String weight = selectedProduct.optString("weight", "N/A");
                            String makingCharge = selectedProduct.optString("wastage", "0");

                            tvProductName.setText(productName);
                            tvKaratValue.setText(karat);
                            tvWeightValue.setText(weight);
                            tvwashtage.setText(makingCharge);

                            Log.d("PRODUCT_DEBUG", "Name: " + productName + ", Weight: " + weight +
                                    ", Karat: " + karat);

                            if (imageUrlArray != null) {
                                List<String> imageUrls = new ArrayList<>();
                                for (int i = 0; i < imageUrlArray.length(); i++) {
                                    String imageUrl = imageUrlArray.getString(i);
                                    imageUrls.add(imageUrl);
                                }
                                setupImageSlider(imageUrls);
                            } else {
                                Toast.makeText(this, "No images available for this product", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
                        Toast.makeText(this, "Error parsing product data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("API_ERROR", "Error: " + error.toString());
                    Toast.makeText(this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }


    private void setupImageSlider(List<String> imageUrls) {
        GiftingSlider adapter = new GiftingSlider(GiftingFulllDetails.this, imageUrls);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            // Dots for the indicator
        }).attach();
    }



    private void openDialer(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private void showLoginAlertDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Login Required")
                .setMessage("You need to log in to add items to your cart.")
                .setPositiveButton("Log In", (dialog, which) -> {
                    // Navigate to LoginActivity
                    Intent intent = new Intent(GiftingFulllDetails.this, LoginActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }


}
