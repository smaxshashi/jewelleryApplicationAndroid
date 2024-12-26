package com.bansal.JewellaryApplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class AddUserDetails extends AppCompatActivity {
        EditText etEmail, etbirthdate, etaddress, etpincode,etspoucedate;
        Spinner spinnerGender;
        String selectedGender = "";
        Button btnUpdateDetails;
        String UserId;
        String impuserid;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);
                EdgeToEdge.enable(this);
                setContentView(R.layout.activity_add_user_details);
                getWindow().setStatusBarColor(ContextCompat.getColor(AddUserDetails.this, R.color.maroon));
                getWindow().setNavigationBarColor(ContextCompat.getColor(AddUserDetails.this, R.color.white));
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                String name = sharedPreferences.getString("name", "Unknown");
                String number = sharedPreferences.getString("mobileNumber", "Unknown");
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                impuserid = sharedPreferences.getString("userId", "");

                UserId = getIntent().getStringExtra("userid");
                etEmail = findViewById(R.id.etEmail);
                etbirthdate = findViewById(R.id.editTextDate);
                etspoucedate = findViewById(R.id.editTextspDate);
                etaddress = findViewById(R.id.etAddress);
                etpincode = findViewById(R.id.etPinCode);
                spinnerGender = findViewById(R.id.spinnerGender);
                btnUpdateDetails = findViewById(R.id.btnUpdateDetails);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                        new String[]{"Select Gender", "Male", "Female", "Other"});
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerGender.setAdapter(adapter);

                // Date Picker for Birth Date
                etbirthdate.setOnClickListener(v -> {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            AddUserDetails.this,
                            (view, year, month, dayOfMonth) -> {
                                String formattedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                                etbirthdate.setText(formattedDate);
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    datePickerDialog.show();
                });  etspoucedate.setOnClickListener(v -> {
                    Calendar calendar = Calendar.getInstance();
                    DatePickerDialog datePickerDialog = new DatePickerDialog(
                            AddUserDetails.this,
                            (view, year, month, dayOfMonth) -> {
                                String formattedDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth);
                                etspoucedate.setText(formattedDate);
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    datePickerDialog.show();
                });

                btnUpdateDetails.setOnClickListener(v -> {



                    String email = etEmail.getText().toString().trim();
                    String address = etaddress.getText().toString().trim();
                    String dateofbirth = etbirthdate.getText().toString().trim();  String spouceateofbirth = etbirthdate.getText().toString().trim();
                    String pincode = etpincode.getText().toString().trim();
                    String gender = spinnerGender.getSelectedItem().toString();

                    Log.d("USER_INPUT", "Email: " + email);
                    Log.d("USER_INPUT", "Address: " + address);
                    Log.d("USER_INPUT", "Date of Birth: " + dateofbirth);                 Log.d("USER_INPUT", "Date of Birth: " + spouceateofbirth);
                    Log.d("USER_INPUT", "Pincode: " + pincode);
                    Log.d("USER_INPUT", "Gender: " + gender);

                    // Validate inputs
                    if (email.isEmpty()) {
                        Toast.makeText(AddUserDetails.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (address.isEmpty()) {
                        Toast.makeText(AddUserDetails.this, "Address cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (dateofbirth.isEmpty()) {
                        Toast.makeText(AddUserDetails.this, "Date of Birth cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    } if (spouceateofbirth.isEmpty()) {
                        Toast.makeText(AddUserDetails.this, "Date of Birth cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (pincode.isEmpty()) {
                        Toast.makeText(AddUserDetails.this, "Pincode cannot be empty", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (gender.equals("Select Gender")) {
                        Toast.makeText(AddUserDetails.this, "Please select a gender", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // If validation passes, call API method
                    updateUserDetails(email, gender, address, pincode,dateofbirth,spouceateofbirth);
                });
            }

    private void updateUserDetails(String email, String gender, String address, String pincode,String dateofbirth,String spoucedate) {
        String url = "https://api.gehnamall.com/auth/updateUser/"+impuserid;

        // Prepare form data
        Map<String, String> userDetails = new HashMap<>();
        userDetails.put("email", email);
        userDetails.put("gender", gender);
        userDetails.put("address", address);
        userDetails.put("pincode", pincode);
        userDetails.put("dateOfBirth",dateofbirth);
        userDetails.put("spouseDob",spoucedate);

        // Log the data being sent
        Log.d("API_REQUEST", "Request Data: " + userDetails.toString());

        // Create a multipart request
        MultipartRequest request = new MultipartRequest(
                Request.Method.POST,
                url,
                userDetails,
                response -> {
                    Log.d("API_RESPONSE", "Response: " + response);

                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String message = jsonResponse.optString("message", "No message");
                        int status = jsonResponse.optInt("status", -1);

                        if (status == 0) {
                            Log.d("API_SUCCESS", message);
                            Intent i = new Intent(AddUserDetails.this,HomeActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(AddUserDetails.this, "Enter Data Properly", Toast.LENGTH_SHORT).show();
                            Log.e("API_FAILURE", "Update failed: " + message);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(AddUserDetails.this, "Enter Data Properly", Toast.LENGTH_SHORT).show();
                        Log.e("API_ERROR", "Invalid response: " + e.getMessage());
                    }
                },
                error -> {
                    if (error.networkResponse != null) {
                        String errorMessage = new String(error.networkResponse.data);
                        try {
                            JSONObject errorJson = new JSONObject(errorMessage);
                            String serverError = errorJson.optString("error", "Unknown error");
                            Log.e("API_ERROR", "Server Error: " + serverError);
                            Toast.makeText(AddUserDetails.this, "Enter Data Properly", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Log.e("API_ERROR", "Invalid error response: " + errorMessage);
                            Toast.makeText(AddUserDetails.this, "Enter Data Properly", Toast.LENGTH_SHORT).show();
                        }
                    }
                    Toast.makeText(this, "Failed to update user details", Toast.LENGTH_SHORT).show();
                }
        );

        // Set a retry policy
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        // Add request to the queue
        Volley.newRequestQueue(this).add(request);
    }

}
