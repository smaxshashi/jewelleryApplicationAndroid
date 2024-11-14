package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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

ImageSlider Imagesliver2;
CardView cvgold,cvcoinsorbar,cvloosedtone,cvloosediamond,cvplatinum,cvslivery,cvgoldjewellary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        Imagesliver2 = view.findViewById(R.id.isUserHomeFragmentOfferImageslider2);
        cvgold=view.findViewById(R.id.cvUserFragmentGold);
        cvcoinsorbar=view.findViewById(R.id.cvUserFragmentCoins);
        cvloosedtone=view.findViewById(R.id.cvUserFragmentloosestone);

        cvloosediamond=view.findViewById(R.id.cvUserFragmentLooseDiamond);
        cvplatinum=view.findViewById(R.id.cvUserFragmentPlatinum);
        cvslivery=view.findViewById(R.id.cvUserFragmentsliver);

        cvgoldjewellary =view.findViewById(R.id.cvUserFragmentGoldjewellary);

        cvgold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CategorywisesubcategoryActivity.class);
                startActivity(i);
            }
        });
        cvcoinsorbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CategorywisesubcategoryActivity.class);
                startActivity(i);
            }
        });
        cvloosedtone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CategorywisesubcategoryActivity.class);
                startActivity(i);
            }
        });
        cvloosediamond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CategorywisesubcategoryActivity.class);
                startActivity(i);
            }
        });
        cvplatinum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CategorywisesubcategoryActivity.class);
                startActivity(i);
            }
        });
        cvslivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CategorywisesubcategoryActivity.class);
                startActivity(i);
            }
        });
        cvgoldjewellary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),CategorywisesubcategoryActivity.class);
                startActivity(i);
            }
        });

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