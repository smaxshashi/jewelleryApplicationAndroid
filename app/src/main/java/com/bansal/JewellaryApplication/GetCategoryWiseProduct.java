package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class GetCategoryWiseProduct extends AppCompatActivity {

    ImageView ivmen, ivwomen;
    String categorycode;
    String mencode, womencode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_category_wise_product); // Ensure this matches your layout XML file

        // Set status bar and navigation bar colors
        getWindow().setStatusBarColor(ContextCompat.getColor(GetCategoryWiseProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetCategoryWiseProduct.this, R.color.white));

        // Get category code from Intent
        categorycode = getIntent().getStringExtra("CategoryCode");

        if (categorycode == null || categorycode.isEmpty()) {
            Toast.makeText(this, "Category code is missing!", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if no category code is provided
            return;
        }

        // Initialize views by referencing their IDs
        ivmen = findViewById(R.id.ivMenImage);
        ivwomen = findViewById(R.id.ivWomenImage);

        // Ensure that the ImageViews are correctly initialized
        if (ivmen == null || ivwomen == null) {
            Log.e("GetCategoryWiseProduct", "ImageView initialization failed");
            return;
        }

        // Gender codes
        womencode = "1";
        mencode = "2";

        // Set click listeners for ImageViews
        ivmen.setOnClickListener(v -> {
            Intent intent = new Intent(GetCategoryWiseProduct.this, GetCtegoryProduct.class);
            intent.putExtra("categorycode", categorycode);
            intent.putExtra("gendercode", mencode);
            startActivity(intent);
        });

        ivwomen.setOnClickListener(v -> {
            Intent intent = new Intent(GetCategoryWiseProduct.this, GetCtegoryProduct.class);
            intent.putExtra("categorycode", categorycode);
            intent.putExtra("gendercode", womencode);
            startActivity(intent);
        });
    }
}
