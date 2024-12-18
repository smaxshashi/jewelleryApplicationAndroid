package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class AddUserDetails extends AppCompatActivity {
     EditText etEmail;
     Spinner spinnerGender;
     String selectedGender = "";
    Button btnUpdateDetails;
    String UserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_user_details);
        getWindow().setStatusBarColor(ContextCompat.getColor(AddUserDetails.this,R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AddUserDetails.this,R.color.white));
        UserId=getIntent().getStringExtra("userid");
        etEmail = findViewById(R.id.etEmail);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnUpdateDetails = findViewById(R.id.btnUpdateDetails);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"Select Gender", "Male", "Female", "Other"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGender.setAdapter(adapter);
        btnUpdateDetails.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String gender = spinnerGender.getSelectedItem().toString();

            // Validate inputs
            if (email.isEmpty()) {
                Toast.makeText(AddUserDetails.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            if (gender.equals("Select Gender")) {
                Toast.makeText(AddUserDetails.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                return;
            }

            // If validation passes, call API method
            updateUserDetails(email, gender);
        });


    }

    private void updateUserDetails(String email, String gender) {
        String url = "https://api.gehnamall.com/auth/update/"+UserId;

        JSONObject userDetails = new JSONObject();
        try {
            userDetails.put("email", email);
            userDetails.put("gender", gender);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                userDetails,
                response -> {
                    Log.d("API_RESPONSE", "Response: " + response.toString());
                    Toast.makeText(this, "User details updated successfully!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    if (error.networkResponse != null) {
                        Log.e("API_ERROR", "HTTP Status Code: " + error.networkResponse.statusCode);
                    }
                    Log.e("API_ERROR", "Error: " + error.toString());
                    Toast.makeText(this, "Failed to update user details", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(this).add(request);
    }




}