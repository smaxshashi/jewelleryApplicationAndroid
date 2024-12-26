package com.bansal.JewellaryApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserRegistratingActivity extends AppCompatActivity {
    EditText etName,etMobileno,etUsername,etPassword,etConfirmPassword;
    AppCompatButton acbtnSignUP;
    ProgressDialog progressDialog;
    String name,number;
    private static final String REGISTER_API_URL = "https://api.gehnamall.com/auth/register";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registrating);
        getWindow().setNavigationBarColor(ContextCompat.getColor(
                UserRegistratingActivity.this,R.color.maroon));
        getWindow().setStatusBarColor(ContextCompat.getColor(UserRegistratingActivity.this,R.color.maroon));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


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
                    name=etName.getText().toString();
                    number=etMobileno.getText().toString();



                   InsertData(name,number);

                }


            }
        });



    }

    private void InsertData(String name, String number) {
        String url = "https://api.gehnamall.com/auth/register?phoneNumber=" + number + "&name=" + name;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int status = response.getInt("status");

                            if (status == -1) {
                                // User exists
                                Toast.makeText(UserRegistratingActivity.this, "User already exists!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(UserRegistratingActivity.this,LoginActivity.class);
                                startActivity(i);
                            } else {
                                // Navigate to OTP page
                                Intent intent = new Intent(UserRegistratingActivity.this, OTPVerificationactivity.class);
                                intent.putExtra("phoneNumber", number);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(UserRegistratingActivity.this, "Error in response parsing!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserRegistratingActivity.this, "API request failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        queue.add(jsonObjectRequest);

    }


}