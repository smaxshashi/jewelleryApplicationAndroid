package com.bansalandsons.JewellaryApplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

public class Termasandcondition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termasandcondition);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

      getWindow().setStatusBarColor(ContextCompat.getColor(Termasandcondition.this,R.color.maroon));getWindow().setNavigationBarColor(ContextCompat.getColor(Termasandcondition.this,R.color.white));
    }
}