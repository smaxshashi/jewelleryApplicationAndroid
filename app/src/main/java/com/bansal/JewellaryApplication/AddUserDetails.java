package com.bansal.JewellaryApplication;

import android.app.DatePickerDialog;
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
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;


    public class AddUserDetails extends AppCompatActivity {
        EditText etEmail, etbirthdate, etaddress, etpincode;
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
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                String name = sharedPreferences.getString("name", "Unknown");
                String number = sharedPreferences.getString("mobileNumber", "Unknown");
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                impuserid = sharedPreferences.getString("userId", "");

                UserId = getIntent().getStringExtra("userid");
                etEmail = findViewById(R.id.etEmail);
                etbirthdate = findViewById(R.id.editTextDate);
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
                });

                btnUpdateDetails.setOnClickListener(v -> {
                    String email = etEmail.getText().toString().trim();
                    String address = etaddress.getText().toString().trim();
                    String dateofbirth = etbirthdate.getText().toString().trim();
                    String pincode = etpincode.getText().toString().trim();
                    String gender = spinnerGender.getSelectedItem().toString();

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
                    updateUserDetails(email, gender, dateofbirth, address, pincode);
                });
            }

        private void updateUserDetails(String email, String gender, String dateOfBirth, String address, String pincode) {
            String url = "https://api.gehnamall.com/auth/updateUser/17";  // Assuming the correct endpoint

            JSONObject userDetails = new JSONObject();
            try {
                userDetails.put("email", email);
                userDetails.put("gender", gender);
                userDetails.put("dateOfBirth", dateOfBirth);  // Ensure date is formatted correctly
                userDetails.put("address", address);
                userDetails.put("pincode", pincode);
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
                        String updatedEmail = response.optString("email", "Not available");
                        String updatedToken = response.optString("token", "Not available");

                        Log.d("Updated Email", updatedEmail);
                        Log.d("Updated Token", updatedToken);

                        if (updatedEmail.equals("null")) {
                            updatedEmail = "Not available"; // Handle null case appropriately
                        }
                        if (updatedToken.equals("null")) {
                            updatedToken = "Not available"; // Handle null case appropriately
                        }

                        // Update UI with retrieved data
                        Log.d("Updated Email", updatedEmail);
                        Log.d("Updated Token", updatedToken);

                        // Check for specific fields
                        String updatedAddress = response.optString("address", "Not available");
                        String updatedPincode = response.optString("pincode", "Not available");
                        String updatedDateOfBirth = response.optString("dateOfBirth", "Not available");

                        Log.d("Updated Address", updatedAddress);
                        Log.d("Updated Pincode", updatedPincode);
                        Log.d("Updated Date of Birth", updatedDateOfBirth);
                    },
                    error -> {
                        if (error.networkResponse != null) {
                            Log.e("API_ERROR", "HTTP Status Code: " + error.networkResponse.statusCode);
                            String errorMessage = new String(error.networkResponse.data);
                            Log.e("API_ERROR", "Error: " + errorMessage);
                        }
                        Log.e("API_ERROR", "Error: " + error.toString());
                        Toast.makeText(this, "Failed to update user details", Toast.LENGTH_SHORT).show();
                    }
            );

            Volley.newRequestQueue(this).add(request);
        }

    }
