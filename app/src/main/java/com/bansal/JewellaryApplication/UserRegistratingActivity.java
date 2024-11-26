package com.bansal.JewellaryApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UserRegistratingActivity extends AppCompatActivity {
    EditText etName,etMobileno,etUsername,etPassword,etConfirmPassword;
    AppCompatButton acbtnSignUP;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registrating);
        getWindow().setNavigationBarColor(ContextCompat.getColor(
                UserRegistratingActivity.this,R.color.maroon));
        getWindow().setStatusBarColor(ContextCompat.getColor(UserRegistratingActivity.this,R.color.maroon));

        etName = findViewById(R.id.etRegistrationActivityName);
        etMobileno = findViewById(R.id.etRegistrationActivityMobileNo);

        acbtnSignUP = findViewById(R.id.acbtnRegistrationActivitySignUP);

        acbtnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("Enter Your Name");
                } else if (etMobileno.getText().toString().isEmpty()) {
                    etMobileno.setError("Enetr the Mobile Number");
                } else if (etMobileno.getText().toString().length() != 10) {
                    etMobileno.setError("Enter  the proper Mobile Number");
                }  else {

                    Intent i = new Intent(UserRegistratingActivity.this,OTPVerificationactivity.class);
                    startActivity(i);

                }


            }
        });



    }
}