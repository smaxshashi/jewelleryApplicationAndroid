package com.bansal.JewellaryApplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Network.NetworkChangeListner;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class LoginActivity extends AppCompatActivity {
    EditText etPhonenumber;

    TextView tvgenerateotp,tvnewuser;
    AppCompatButton acbtnLogin,acbtnCancleLogin;
    ProgressDialog progressDialog;
    private static final String API_URL = "https://api.gehnamall.com/auth/check-user?phoneNumber=";
    NetworkChangeListner networkChangeListner = new NetworkChangeListner();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setNavigationBarColor(ContextCompat.getColor(
                LoginActivity.this,R.color.maroon));
        getWindow().setStatusBarColor(ContextCompat.getColor(LoginActivity.this,R.color.maroon));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        int screenWidth = getResources().getDisplayMetrics().widthPixels;




        etPhonenumber =  findViewById(R.id.etLoginActivityPhonenumber);
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
                    String phoneNumber = etPhonenumber.getText().toString().trim();
                    checkUser(phoneNumber);
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



    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListner,intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(networkChangeListner);
    }


    private void checkUser(String phoneNumber) {
        String url = API_URL + phoneNumber;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        // Log server response for debugging
//                        Toast.makeText(LoginActivity.this, "Server Response: " + response, Toast.LENGTH_LONG).show();

                        JSONObject jsonResponse = new JSONObject(response);

                        String status = jsonResponse.optString("status");

                        if ("0".equals(status)) {
                            // Jump to OTP activity if status is 0
                            Intent intent = new Intent(LoginActivity.this, OTPVerificationactivity.class);
                            intent.putExtra("phoneNumber", phoneNumber);
                            startActivity(intent);
                            finish();
                        } else {
                            // Toast if user doesn't exist
                            Toast.makeText(LoginActivity.this, "User does not exist. Please sign up.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        // Handle JSON parsing errors
                        Toast.makeText(LoginActivity.this, "Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    // Handle network error
                    Toast.makeText(LoginActivity.this, "Network Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(request);
    }















}


