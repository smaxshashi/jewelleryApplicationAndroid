package com.bansal.JewellaryApplication;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.activity.contextaware.ContextAware;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class AboutUs extends AppCompatActivity {
    ImageSlider imageslider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);
        getWindow().setStatusBarColor(ContextCompat.getColor(AboutUs.this,R.color.maroon));
        getWindow().setNavigationBarColor(ContextCompat.getColor(AboutUs.this,R.color.white));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        imageslider = findViewById(R.id.imagesliderwelcome);
        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.img1, ScaleTypes.CENTER_INSIDE));
        slideModelArrayList.add(new SlideModel(R.drawable.img3, ScaleTypes.CENTER_INSIDE));
        slideModelArrayList.add(new SlideModel(R.drawable.img5, ScaleTypes.CENTER_INSIDE));
        slideModelArrayList.add(new SlideModel(R.drawable.img6, ScaleTypes.CENTER_INSIDE));
        slideModelArrayList.add(new SlideModel(R.drawable.img7, ScaleTypes.CENTER_INSIDE));
        slideModelArrayList.add(new SlideModel(R.drawable.img8, ScaleTypes.CENTER_INSIDE));
        imageslider.setImageList(slideModelArrayList);
        imageslider.setSlideAnimation(AnimationTypes.ZOOM_IN);

    }
}