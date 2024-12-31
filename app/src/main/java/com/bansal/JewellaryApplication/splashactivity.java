package com.bansal.JewellaryApplication;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
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

        // Make the video full screen and hide system UI
        videoView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        // Set up a listener to prepare the video
        videoView.setOnPreparedListener(mp -> {
            mp.setLooping(false); // Optional: Set to true if you want looping
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING); // Full-screen scaling
            videoView.start(); // Start playback once ready
        });

        // Navigate to the next activity after the video finishes
        videoView.setOnCompletionListener(mp -> navigateToNextActivity());

        // Optional: Backup delay if video duration is unknown
        new Handler().postDelayed(this::navigateToNextActivity, 6000); // Adjust to match the video duration
    }

    // Method to navigate to the next activity
    private void navigateToNextActivity() {
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
    }
}