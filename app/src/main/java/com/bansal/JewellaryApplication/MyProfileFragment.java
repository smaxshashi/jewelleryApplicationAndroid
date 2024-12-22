package com.bansal.JewellaryApplication;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpCookie;

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
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;

    TextView tvusername,tvnumber;
    String name,number;
    Button addemail;
    String UserId;
    TextView tvlogin;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_myprofil, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);
       name=sharedPreferences.getString("name","Unknow");
       number=sharedPreferences.getString("mobileNumber","Unknow");
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        UserId=sharedPreferences.getString("userId","");

        // Initialize the views
        imageView = view.findViewById(R.id.profileImage);
        imageButton = view.findViewById(R.id.editPhotoButton);
        tvnumber=view.findViewById(R.id.tvmobileNumber);
        tvusername=view.findViewById(R.id.tvuserName);
        addemail = view.findViewById(R.id.addEmailButton);
        tvlogin = view.findViewById(R.id.tvlogin);





        addemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),AddUserDetails.class);
                i.putExtra("userid",UserId);
                startActivity(i);

            }
        });

        tvusername.setText(name);
        tvnumber.setText(number);

        // Set click listener for the ImageButton
        // Assuming you store login state and UserId in SharedPreferences
        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);
        String UserId = sharedPreferences.getString("UserId", null);

        imageButton.setOnClickListener(v -> {

                // Open gallery if logged in
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);

        });


        return view;
    }
    @Override
    public void onResume() {
        super.onResume();

        // Retrieve UserId from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);
        String UserId = sharedPreferences.getString("UserId", null);

        if (UserId == null) {
            // User is not logged in - Show "Login" option
            tvlogin.setText("Login");
            tvlogin.setOnClickListener(v -> {
                // Navigate to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            });
        } else {
            // User is logged in - Show "Logout" option
            tvlogin.setText("Logout");
            tvlogin.setOnClickListener(v -> {
                // Clear login state from SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("UserId"); // Remove UserId to log out
                editor.apply();

                // Navigate back to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
                startActivity(intent);

                // Show a toast message
                Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            });
        }
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
        // Convert bitmap to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 80, byteArrayOutputStream);
        byte[] imageData = byteArrayOutputStream.toByteArray();

        // Create multipart request body
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("images", "profile_image.webp",
                        RequestBody.create(imageData, MediaType.parse("image/webp")))
                .build();

        // Make POST request
        new OkHttpClient().newCall(new Request.Builder()
                .url("https://api.gehnamall.com/auth/update/" + userId)
                .post(requestBody)
                .build()
        ).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                // Run on UI thread

                        Toast.makeText(getActivity(), "Failed to upload image", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    // Create a Handler to post the Toast on UI thread
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(getActivity(), "Profile image updated successfully", Toast.LENGTH_SHORT).show()
                    );
                } else {
                    // Create a Handler to post the Toast on UI thread
                    new Handler(Looper.getMainLooper()).post(() ->
                            Toast.makeText(getActivity(), "Failed to update profile image", Toast.LENGTH_SHORT).show()
                    );
                }
            }

        });
    }


    // Helper method to show toast on UI thread


}
