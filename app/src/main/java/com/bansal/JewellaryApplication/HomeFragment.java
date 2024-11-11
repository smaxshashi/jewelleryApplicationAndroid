package com.bansal.JewellaryApplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

ImageSlider Imageslider;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        Imageslider = view.findViewById(R.id.isUserHomeFragmentOfferImageslider);

        ArrayList<SlideModel> slideModelsArrayList = new ArrayList<>();
        slideModelsArrayList.add(new SlideModel(R.drawable.off1, ScaleTypes.CENTER_CROP));
        slideModelsArrayList.add(new SlideModel(R.drawable.off2, ScaleTypes.CENTER_CROP));
        slideModelsArrayList.add(new SlideModel(R.drawable.off3, ScaleTypes.CENTER_CROP));
        slideModelsArrayList.add(new SlideModel(R.drawable.off4, ScaleTypes.CENTER_CROP));
        Imageslider.setImageList(slideModelsArrayList);
        Imageslider.setSlideAnimation(AnimationTypes.ZOOM_IN);
        return view;
    }
}