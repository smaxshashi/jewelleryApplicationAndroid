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
        getWindow().setNavigationBarColor(ContextCompat.getColor(splashactivity.this,R.color.white));
        getWindow().setStatusBarColor(ContextCompat.getColor(splashactivity.this,R.color.white));
        VideoView videoView = findViewById(R.id.splashVideoView);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bansal);
        videoView.setVideoURI(videoUri);
        videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
        videoView.start();






        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
                if (isLoggedIn) {
                    // User is already logged in, navigate to HomeActivity
                    Intent intent = new Intent(splashactivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    // User is not logged in, navigate to LoginActivity
                    Intent intent = new Intent(splashactivity.this, LoginActivity.class);
                    startActivity(intent);
                }

                finish();

            }
        },6000);




    }
}