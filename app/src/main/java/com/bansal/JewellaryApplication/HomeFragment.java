package com.bansal.JewellaryApplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGetALLcategory;
import com.bansal.JewellaryApplication.pojoclasses.POJOGetallcategory;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

ImageSlider Imagesliver2;
RecyclerView rvlistofcategory;
List<POJOGetallcategory> pojoGetallcategories;
AdpterGetALLcategory adpterGetALLcategory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        Imagesliver2 = view.findViewById(R.id.isUserHomeFragmentOfferImageslider2);
        rvlistofcategory = view.findViewById(R.id.rvUserHomeFragmentcategorylist);
        pojoGetallcategories =  new ArrayList<>();

        ArrayList<SlideModel> slideModelsArrayList2 = new ArrayList<>();
        slideModelsArrayList2.add(new SlideModel(R.drawable.off1, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off2, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off3, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off4, ScaleTypes.CENTER_CROP));
        Imagesliver2.setImageList(slideModelsArrayList2);
        Imagesliver2.setSlideAnimation(AnimationTypes.ZOOM_IN);

        rvlistofcategory.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));


        GetCategoryofProduct();
        return view;

    }

    private void GetCategoryofProduct() {
        String url = "http://3.228.126.226:8080/api/categories";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObj = response.getJSONObject(i);
                                String categoryName = categoryObj.getString("categoryName");
                                String categoryCode = categoryObj.getString("categoryCode");
                                String categoryImage = categoryObj.getString("exfield1");

                                pojoGetallcategories.add(new POJOGetallcategory(categoryName,categoryCode,categoryImage));
                            }
                            adpterGetALLcategory = new AdpterGetALLcategory(pojoGetallcategories,getActivity());
                            rvlistofcategory.setAdapter(adpterGetALLcategory);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}



