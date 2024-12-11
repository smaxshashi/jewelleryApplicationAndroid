package com.bansal.JewellaryApplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.bansal.JewellaryApplication.Adapterclasses.AdpterSliderSoulmet;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HimorherFullProductdettail extends AppCompatActivity {
    ViewPager2 viewPager;
    TextView tvProductName, tvKaratValue, tvWeightValue, tvCompanyName,tvwashtage;
    Button btnAddToWishlist;
    String productId="2",soulmet;

    LinearLayout ivWhatsapp, llCall;
    TextView tvdis;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_himorher_full_productdettail);
        getWindow().setNavigationBarColor(ContextCompat.getColor(HimorherFullProductdettail.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(HimorherFullProductdettail.this,R.color.maroon));


        soulmet=getIntent().getStringExtra("soulmet");
        productId=getIntent().getStringExtra("productId");

        viewPager = findViewById(R.id.viewPager);
        tvProductName = findViewById(R.id.tvProductName);
        tvKaratValue = findViewById(R.id.tvKaratValue);
        tvWeightValue = findViewById(R.id.tvWeightValue);
        tvwashtage=findViewById(R.id.tvMakingChargeValue);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        btnAddToWishlist = findViewById(R.id.btnAddToCart);
        ivWhatsapp=findViewById(R.id.llWhatsapp);
        tvdis=findViewById(R.id.tvdis);
        tvdis.setText(soulmet);
        tabLayout = findViewById(R.id.tabLayout);
        llCall = findViewById(R.id.llCall);
        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialer("+9191982031621"); // Replace with your phone number
            }
        });


        ivWhatsapp.setOnClickListener(new View.OnClickListener() {
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



        fetchProductDetails(productId);
    }

    private void fetchProductDetails(String productId) {
        String url = "https://api.gehnamall.com/api/getProducts?soulmate="+soulmet+"&wholeseller=test";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        Log.d("API_RESPONSE", response.toString());

                        JSONArray productsArray = response.getJSONArray("products");
                        JSONArray imageUrlArray = response.getJSONArray("imageUrl");

                        JSONObject selectedProduct = null;

                        // Debug all product IDs
                        for (int i = 0; i < productsArray.length(); i++) {
                            JSONObject product = productsArray.getJSONObject(i);
                            Log.d("PRODUCT_DEBUG", "Product ID: " + product.getInt("productId"));
                            if (product.getInt("productId") == Integer.parseInt(productId)) {
                                selectedProduct = product;
                                break;
                            }
                        }

                        // Debug all image URLs
                        List<String> imageUrls = new ArrayList<>();
                        for (int i = 0; i < imageUrlArray.length(); i++) {
                            JSONObject image = imageUrlArray.getJSONObject(i);
                            Log.d("IMAGE_DEBUG", "Image Product ID: " + image.getInt("productId") + ", URL: " + image.getString("imageUrl"));
                            if (image.getInt("productId") == Integer.parseInt(productId)) {
                                imageUrls.add(image.getString("imageUrl"));
                            }
                        }

                        if (selectedProduct != null) {
                            String productName = selectedProduct.getString("productName");
                            String Karat = selectedProduct.getString("karat");
                            String weight = selectedProduct.getString("weight");
                            String MakingCharge = selectedProduct.getString("wastage");

                            tvProductName.setText(productName);
                            tvKaratValue.setText(Karat);
                            tvWeightValue.setText(weight);
                            tvwashtage.setText(MakingCharge);

                            setupImageSlider(imageUrls);
                        } else {
                            Toast.makeText(HimorherFullProductdettail.this, "Product not found", Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(HimorherFullProductdettail.this, "Error parsing product data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e("API_ERROR", error.toString());
                    Toast.makeText(HimorherFullProductdettail.this, "Failed to fetch product details", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
    private void setupImageSlider(List<String> imageUrls) {
        AdpterSliderSoulmet adapter=new AdpterSliderSoulmet(HimorherFullProductdettail.this,imageUrls);
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
    }
