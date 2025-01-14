package com.bansalandsons.JewellaryApplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class splashactivity extends AppCompatActivity {
    ImageView ivAppLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashactivity);

        getWindow().setNavigationBarColor(ContextCompat.getColor(splashactivity.this,R.color.white));

        VideoView videoView = findViewById(R.id.splashVideoView);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, android.R.color.white));
        }

        // Ensure dark status bar icons for better visibility (API 23+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

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
                        mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                        // Full-screen scaling
                    videoView.seekTo(1800);
                        videoView.start(); // Start playback once ready
                    });


                    // Navigate to the next activity after the video fini // Navigate to the next activity after the video finishes
        //    videoView.setOnCompletionListener(mp -> navigateToNextActivity())
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