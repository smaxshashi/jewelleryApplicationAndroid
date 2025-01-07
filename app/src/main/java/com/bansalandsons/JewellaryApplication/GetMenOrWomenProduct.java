package com.bansalandsons.JewellaryApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

public class GetMenOrWomenProduct extends AppCompatActivity {

   CardView ivmen,ivwomen;
    String men="1",womem="2";
    String categorycode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_men_or_women_product);


        getWindow().setStatusBarColor(ContextCompat.getColor(GetMenOrWomenProduct.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(GetMenOrWomenProduct.this, R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        categorycode=getIntent().getStringExtra("CategoryCode");

       ivmen=findViewById(R.id.cvHomeFragmentHIM);
       ivwomen=findViewById(R.id.cvHomeFragmentHER);
        ivmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movetonextmen(categorycode,men);
            }
        });   ivwomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movetonextmen(categorycode,womem);
            }
        });





    }

    private void Movetonextmen(String categorycode, String gendercode) {
        Intent i = new Intent(GetMenOrWomenProduct.this, WomenMenGoldProduct.class);
        i.putExtra("categorycode",categorycode);
        i.putExtra("gendercode",gendercode);
        startActivity(i);

    }


}
