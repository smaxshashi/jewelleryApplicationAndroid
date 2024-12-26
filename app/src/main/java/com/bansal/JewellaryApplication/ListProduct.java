package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ListProduct extends AppCompatActivity {
    TextView tvname;
    RecyclerView rvlist;
    String categorycode,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_product);
        getWindow().setNavigationBarColor(ContextCompat.getColor(ListProduct.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(ListProduct.this,R.color.maroon));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        categorycode=getIntent().getStringExtra("categorycode");
        name=getIntent().getStringExtra("name");

        tvname=findViewById(R.id.tvlightname);
        tvname.setText(name);

    }
}