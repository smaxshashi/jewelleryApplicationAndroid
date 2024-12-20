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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


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
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open gallery
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
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
            String log = "Login";
            tvlogin.setText(log);
            tvlogin.invalidate();
            tvlogin.setOnClickListener(v -> {
                // Navigate to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            });
        } else {
            // User is logged in - Show "Logout" option
            String log2 = "Logout";
            tvlogin.setText(log2);
            tvlogin.invalidate();
            tvlogin.setOnClickListener(v -> {
                // Clear login state from SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("UserId"); // Remove UserId to log out
                editor.apply();

                // Navigate back to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear back stack
                startActivity(intent);

                // Optionally, show a toast message
                Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            });
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();

            try {
                // Display the selected image in the ImageView
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Failed to load image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
