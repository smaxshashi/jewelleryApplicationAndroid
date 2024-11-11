package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class OTPVerificationactivity extends AppCompatActivity {
    AppCompatButton acbtnverifyotp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverificationactivity);
        getWindow().setNavigationBarColor(ContextCompat.getColor(
                OTPVerificationactivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(OTPVerificationactivity.this, R.color.white));

        acbtnverifyotp = findViewById(R.id.acbtnOTPverification);
        acbtnverifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(OTPVerificationactivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

    }
}