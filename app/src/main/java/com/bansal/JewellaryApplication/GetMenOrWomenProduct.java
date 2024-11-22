package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GetMenOrWomenProduct extends AppCompatActivity {
    ImageView ivmen, ivwomen;
    String Categorycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_men_or_women_product);

        // Set status and navigation bar colors
        getWindow().setStatusBarColor(ContextCompat.getColor(GetMenOrWomenProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetMenOrWomenProduct.this, R.color.white));

        // Get CategoryCode from Intent
        Categorycode = getIntent().getStringExtra("CategoryCode");

        // Initialize image views
        ivwomen = findViewById(R.id.ivWomenImage);
        ivmen = findViewById(R.id.ivMenImage);

        // Set click listeners
        ivmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // GenderCode for men is "2"
                saveGenderCode("2");
                moveToProductList("2");
            }
        });

        ivwomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // GenderCode for women is "1"
                saveGenderCode("1");
                moveToProductList("1");
            }
        });
    }

    // Method to navigate to ProductList with CategoryCode and GenderCode
    private void moveToProductList(String GenderCode) {
        Intent intent = new Intent(GetMenOrWomenProduct.this, ProductList.class);
        intent.putExtra("CategoryCode", Categorycode);
        intent.putExtra("GenderCode", GenderCode);
        startActivity(intent);
    }

    // Method to save GenderCode in SharedPreferences
    private void saveGenderCode(String genderCode) {
        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("GenderCode", genderCode); // Save the gender code
        editor.apply(); // Commit changes
    }
}
