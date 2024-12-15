package com.bansal.JewellaryApplication;

import android.content.Intent;
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

public class OTPVerificationactivity extends AppCompatActivity {
    AppCompatButton acbtnverifyotp;
    TextView tvnumber;
    String number;
    EditText etotp1,etotp2,etotp3,etotp4;

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
                String otp=etotp1.getText().toString()+etotp2.getText().toString()+etotp3.getText().toString();

                getVerify();


            }
        });

    }

    private void getVerify() {

    }


}