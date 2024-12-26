package com.bansal.JewellaryApplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Termasandcondition extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_termasandcondition);
      getWindow().setStatusBarColor(ContextCompat.getColor(Termasandcondition.this,R.color.maroon));getWindow().setNavigationBarColor(ContextCompat.getColor(Termasandcondition.this,R.color.white));
    }
}