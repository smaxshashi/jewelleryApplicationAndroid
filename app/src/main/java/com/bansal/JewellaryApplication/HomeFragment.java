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

ImageSlider Imageslider,Imagesliver2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        Imageslider = view.findViewById(R.id.isUserHomeFragmentOfferImageslider);
        Imagesliver2 = view.findViewById(R.id.isUserHomeFragmentOfferImageslider2);

        ArrayList<SlideModel> slideModelsArrayList = new ArrayList<>();
        slideModelsArrayList.add(new SlideModel(R.drawable.off1, ScaleTypes.CENTER_CROP));
        slideModelsArrayList.add(new SlideModel(R.drawable.off2, ScaleTypes.CENTER_CROP));
        slideModelsArrayList.add(new SlideModel(R.drawable.off3, ScaleTypes.CENTER_CROP));
        slideModelsArrayList.add(new SlideModel(R.drawable.off4, ScaleTypes.CENTER_CROP));
        Imageslider.setImageList(slideModelsArrayList);
        Imageslider.setSlideAnimation(AnimationTypes.ZOOM_IN);
        ArrayList<SlideModel> slideModelsArrayList2 = new ArrayList<>();
        slideModelsArrayList2.add(new SlideModel(R.drawable.off1, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off2, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off3, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off4, ScaleTypes.CENTER_CROP));
        Imagesliver2.setImageList(slideModelsArrayList2);
        Imagesliver2.setSlideAnimation(AnimationTypes.ZOOM_IN);
        return view;
    }
}