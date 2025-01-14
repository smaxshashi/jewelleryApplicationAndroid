package com.bansalandsons.JewellaryApplication;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyProfileFragment extends Fragment {

    private ImageButton imageButton;
    private CircleImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;

    TextView tvusername,tvnumber;
    String name,number;
    Button addemail;
    String UserId;
    TextView tvlogin;
    TextView tvemailvalue,tvbirthdate,tvspouceaddress,tvaddress,tvpincode;
    Button logout;
    String ImpuserId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myprofil, container, false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "Unknown");
        String number = sharedPreferences.getString("mobileNumber", "Unknown");
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
         ImpuserId = sharedPreferences.getString("userId", "");

        Log.d("SharedPreferences", "name: " + name + ", number: " + number + ", userId: " + ImpuserId + ", isLoggedIn: " + isLoggedIn);
        // Initialize the views
        imageView = view.findViewById(R.id.profileImage);
        imageButton = view.findViewById(R.id.editPhotoButton);

        tvusername=view.findViewById(R.id.tvuserName);
        addemail = view.findViewById(R.id.addEmailButton);
        tvemailvalue=view.findViewById(R.id.emailValue);
        tvbirthdate=view.findViewById(R.id.dobValue);
        tvpincode=view.findViewById(R.id.pincodeValue);
        tvspouceaddress=view.findViewById(R.id.spouseDobValue);
        tvaddress=view.findViewById(R.id.addressValue);
        logout=view.findViewById(R.id.logoutButton);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // Clear SharedPreferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();  // Save changes

                            // Navigate to LoginActivity
                            Intent intent = new Intent(getActivity(),LoginActivity.class);
                            startActivity(intent);
                            // Finish current activity
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();

            }
        });





        addemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),AddUserDetails.class);
                i.putExtra("userid",ImpuserId);
                startActivity(i);

            }
        });


        // Set click listener for the ImageButton
        // Assuming you store login state and UserId in SharedPreferences
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);
        String UserId = sharedPreferences.getString("UserId", null);

        imageButton.setOnClickListener(v -> {

                // Open gallery if logged in
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

        });


        fetchData(ImpuserId);


        return view;
    }

    private void fetchData(String userId) {
        // Create the AsyncHttpClient instance
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d("FETCH_DATA", "Fetching data for userId: " + userId);


        // Construct the URL dynamically using userId
        String url = "https://api.gehnamall.com/auth/getUserDetail/"+ImpuserId;

        RequestHandle requestHandle = client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String name = response.optString("name", "Default Name");
                    String email = response.optString("email","@.com");
                    String imageUrl = response.optString("image", "null");
                    String address = response.optString("address", "null");
String dateofbirth = response.optString("dateOfBirth", "null");
String pincode=response.optString("pincode","000000");
String spodate= response.optString("spouseDob","null");

                    if (tvusername != null) {
                        tvusername.setText(name);
                    }

                    if (tvemailvalue != null) {
                        tvemailvalue.setText(email);
                    }  if (tvaddress != null) {
                        tvaddress.setText(address);
                    } if (tvbirthdate != null) {
                        tvbirthdate.setText(dateofbirth);
                    } if (tvspouceaddress != null) {
                        tvspouceaddress.setText(spodate);
                    } if (tvpincode != null) {
                        tvpincode.setText(pincode);
                    }

                    if (imageView != null) {
                        Glide.with(requireContext())
                                .load(imageUrl)
                                .error(R.drawable.baseline_person_24)
                                .into(imageView);
                    }

                } catch (Exception e) {

                    Toast.makeText(getActivity(), "Please Log In", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                try {
                    // Log error details
                    Toast.makeText(getActivity(), "Please login", Toast.LENGTH_SHORT).show();

                    // Handle error response
                    String errorMessage = (errorResponse != null)
                            ? errorResponse.optString("message", "Network Unable to fetch.")
                            : "Network Unable to fetch.";

                    Toast.makeText(getActivity(), "Please login", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Please login", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();

            try {
                // Load selected image as Bitmap
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                // Upload image to server
                uploadImage(bitmap, UserId);

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadImage(Bitmap bitmap, String userId) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] imageData = byteArrayOutputStream.toByteArray();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("profileImage", "profile_image.jpg",
                        RequestBody.create(imageData, MediaType.parse("image/jpeg")))
                .build();

        String url = "https://api.gehnamall.com/auth/updateUser/"+ImpuserId;

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.e("UploadError", "Failed to upload image", e);
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                String message = response.isSuccessful() ?
                        "Profile image updated successfully" :
                        "Failed to update profile image: " + response.code();
                Log.d("UploadResponse", "Response: " + response.code() + " - " + response.message());
                new Handler(Looper.getMainLooper()).post(() ->
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show()
                );
            }
        });
    }


    // Helper method to show toast on UI thread


}
