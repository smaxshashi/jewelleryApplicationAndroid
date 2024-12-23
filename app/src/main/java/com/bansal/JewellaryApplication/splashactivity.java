package com.bansal.JewellaryApplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splashactivity extends AppCompatActivity {
    ImageView ivAppLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);

        VideoView videoView = findViewById(R.id.splashVideoView);

        // Set the URI for the video
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bansal);
        videoView.setVideoURI(videoUri);

        // Make the video full screen
        videoView.setZOrderOnTop(true); // Ensures the video starts without a black screen
        videoView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Set up a listener to start the video as soon as it is prepared
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(false); // Optional: Set to true if you want the video to loop
            videoView.start();    // Start video playback once ready
        });

        // Navigate to the next activity after the video duration
        new Handler().postDelayed(() -> {
            SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
            Intent intent;
            if (isLoggedIn) {
                intent = new Intent(splashactivity.this, HomeActivity.class);
            } else {
                intent = new Intent(splashactivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, 6000); // Adjust delay based on the video duration



    }
}