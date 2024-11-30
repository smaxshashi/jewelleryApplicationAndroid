package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DimaondMenWOmen extends AppCompatActivity {
    CardView cvmen,cvwomen;
    String categorycode;
    String men="1",womem="2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dimaond_men_women);
        getWindow().setStatusBarColor(ContextCompat.getColor(DimaondMenWOmen.this, R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(DimaondMenWOmen.this, R.color.white));

        categorycode=getIntent().getStringExtra("CategoryCode");

        cvmen=findViewById(R.id.cvHomeFragmentHIM);
        cvwomen=findViewById(R.id.cvHomeFragmentHER);

        cvmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movetonextmen(categorycode,men);
            }
        });   cvwomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Movetonextmen(categorycode,womem);
            }
        });






    }

    private void Movetonextmen(String categorycode, String gendercode){
        Intent i = new Intent(DimaondMenWOmen.this, DiamondProductLIst.class);
        i.putExtra("categorycode",categorycode);
        i.putExtra("gendercode",gendercode);
        startActivity(i);

    }


}