package com.bansal.JewellaryApplication;

import android.app.ProgressDialog;
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

public class LoginActivity extends AppCompatActivity {
    EditText etPhonenumber;

    TextView tvgenerateotp,tvnewuser;
    AppCompatButton acbtnLogin,acbtnCancleLogin;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setNavigationBarColor(ContextCompat.getColor(
                LoginActivity.this,R.color.maroon));
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this,R.color.maroon));


        etPhonenumber =  findViewById(R.id.etLoginActivityPhonenumber);
        tvgenerateotp =  findViewById(R.id.tvLoginActivityGenerateotp);
        acbtnLogin = findViewById(R.id.acbtnLoginActivityLogin);
        acbtnCancleLogin = findViewById(R.id.acbtnLoginActivityCancleLogin);
        tvnewuser = findViewById(R.id.tvLoginActivityNewuser);

        tvnewuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,UserRegistratingActivity.class);
                startActivity(i);
            }
        });



        acbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPhonenumber.getText().toString().isEmpty()) {
                    etPhonenumber.setError("Enetr the Mobile Number");
                } else if (etPhonenumber.getText().toString().length() != 10) {
                    etPhonenumber.setError("Enter  the proper Mobile Number");
                }else{
                    Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                    startActivity(i);
                }
            }
        });

        acbtnCancleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(i);
            }
        });

        tvgenerateotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "OTP sent to entered Phone Number", Toast.LENGTH_SHORT).show();
            }
        });

    }
}