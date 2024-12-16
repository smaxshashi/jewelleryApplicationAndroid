package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class OTPVerificationactivity extends AppCompatActivity {
    AppCompatButton acbtnverifyotp;
    TextView tvnumber;
    String number;
    EditText etotp1,etotp2,etotp3,etotp4;
    String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverificationactivity);
        getWindow().setNavigationBarColor(ContextCompat.getColor(
                OTPVerificationactivity.this, R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(OTPVerificationactivity.this, R.color.white));

        number=getIntent().getStringExtra("phoneNumber");


        tvnumber=findViewById(R.id.tvOTPverificationtMobileno);
        tvnumber.setText(number);
        etotp1=findViewById(R.id.etOTPverificationotp1);
        etotp2=findViewById(R.id.etOTPverificationotp2);
        etotp3=findViewById(R.id.etOTPverificationotp3);




        acbtnverifyotp = findViewById(R.id.acbtnOTPverification);
        acbtnverifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etotp1.getText().toString().isEmpty() || etotp2.getText().toString().isEmpty() || etotp3.getText().toString().isEmpty()){
                    Toast.makeText(OTPVerificationactivity.this,"enter proper otp",Toast.LENGTH_SHORT).show();
                }
                otp=etotp1.getText().toString()+etotp2.getText().toString()+etotp3.getText().toString();

                getVerify();


            }
        });

    }

    private void getVerify() {

        String verifyUrl = "https://api.gehnamall.com/auth/otpVerify?phoneNumber=" +number+ "&otp=" +otp;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest otpVerifyRequest = new StringRequest(Request.Method.POST, verifyUrl,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String status = jsonResponse.optString("status");

                        if ("0".equals(status)) {
                            // OTP verified successfully, proceed to next activity
                            Intent intent = new Intent(OTPVerificationactivity.this, HomeActivity.class);
                            String userId = jsonResponse.optString("userId");
                            String name = jsonResponse.optString("name");
                            String mobileNumber = jsonResponse.optString("mobileNumber");

                            // Save user details in SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userId", userId);
                            editor.putString("name", name);
                            editor.putString("mobileNumber", mobileNumber);
                            editor.apply();
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(OTPVerificationactivity.this, "Invalid OTP. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(OTPVerificationactivity.this, "Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    Toast.makeText(OTPVerificationactivity.this, "Network Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(otpVerifyRequest);
    }


}