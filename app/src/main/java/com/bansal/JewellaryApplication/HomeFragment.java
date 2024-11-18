package com.bansal.JewellaryApplication;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGetALLcategory;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGetPrice;
import com.bansal.JewellaryApplication.pojoclasses.POJOGetallcategory;
import com.bansal.JewellaryApplication.pojoclasses.POJOgetPrice;
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
RecyclerView rvlistofcategory,rvMakrketpricelist;
List<POJOGetallcategory> pojoGetallcategories;
List<POJOgetPrice> pojOgetPrices;
AdpterGetPrice adpterGetPrice;
AdpterGetALLcategory adpterGetALLcategory;

ImageView ivinstgarm,ivfacebook,ivyoutube,ivprintrest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);

        Imagesliver2 = view.findViewById(R.id.isUserHomeFragmentOfferImageslider2);
        rvlistofcategory = view.findViewById(R.id.rvUserHomeFragmentcategorylist);
        rvMakrketpricelist = view.findViewById(R.id.rvUserHomeFragmentListmaket);
        ivinstgarm = view.findViewById(R.id.ivinstgarm);
        ivfacebook = view.findViewById(R.id.ivFacebookk);
        ivyoutube = view.findViewById(R.id.ivYoutube);
        ivprintrest = view.findViewById(R.id.ivPrintrest);
        pojoGetallcategories =  new ArrayList<>();
        pojOgetPrices = new ArrayList<>();

        ArrayList<SlideModel> slideModelsArrayList2 = new ArrayList<>();
        slideModelsArrayList2.add(new SlideModel(R.drawable.off1, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off2, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off3, ScaleTypes.CENTER_CROP));
        slideModelsArrayList2.add(new SlideModel(R.drawable.off4, ScaleTypes.CENTER_CROP));
        Imagesliver2.setImageList(slideModelsArrayList2);
        Imagesliver2.setSlideAnimation(AnimationTypes.ZOOM_IN);

        rvlistofcategory.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));
        rvMakrketpricelist.setLayoutManager(new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false));

        // Instagram Button
        
        ivinstgarm.setOnClickListener(v -> openUrl("https://www.instagram.com/parth_pd11/"));

        // Facebook Button
        
        ivfacebook.setOnClickListener(v -> openUrl("https://www.facebook.com/"));

        // Pinterest Button
        
        ivyoutube.setOnClickListener(v -> openUrl("https://www.youtube.com/watch?v=buSdqtdn_4I"));

        // YouTube Button
        
        ivprintrest.setOnClickListener(v ->
                openUrl("https://in.pinterest.com/"));


        GetCategoryofProduct();
        GetPriceofMakket();

        return view;

    }

    private void GetPriceofMakket() {
        String url = "http://3.110.34.172:8080/admin/prices";
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        // Create a JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Clear existing data to avoid duplication
                            pojOgetPrices.clear();

                            // Log the raw API response for debugging
                            Log.d("API Response", response.toString());

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                // Debug: Log the content of each JSON object
                                Log.d("JSON Object", jsonObject.toString());

                                // Parse the values from the JSON object
                                double k24 = jsonObject.optDouble("24k", Double.NaN);
                                double k22 = jsonObject.optDouble("22k", Double.NaN);
                                double k18 = jsonObject.optDouble("18k", Double.NaN);
                                double k14 = jsonObject.optDouble("14k", Double.NaN);
                                String metal = jsonObject.optString("metalName", "Unknown");

                                // Handle NaN or invalid values for display
                                String str24 = (Double.isNaN(k24) || k24 == 0.0) ? "Not Available" : String.format("%.2f", k24);
                                String str22 = (Double.isNaN(k22) || k22 == 0.0) ? "Not Available" : String.format("%.2f", k22);
                                String str18 = (Double.isNaN(k18) || k18 == 0.0) ? "Not Available" : String.format("%.2f", k18);
                                String str14 = (Double.isNaN(k14) || k14 == 0.0) ? "Not Available" : String.format("%.2f", k14);

                                // Add the parsed data to the list
                                pojOgetPrices.add(new POJOgetPrice(str24, str22, str18, str14, metal));
                            }

                            // Initialize adapter and bind it to RecyclerView
                            adpterGetPrice = new AdpterGetPrice(pojOgetPrices, getActivity());
                            rvMakrketpricelist.setAdapter(adpterGetPrice);

                        } catch (JSONException e) {
                            // Log JSON parsing errors
                            Log.e("GetPriceofMakket", "JSON Parsing error: " + e.getMessage());
                            Toast.makeText(requireContext(), "Error parsing data.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log Volley errors
                        Log.e("GetPriceofMakket", "Volley error: " + error.getMessage());
                        Toast.makeText(requireContext(), "Error fetching data.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the RequestQueue
        requestQueue.add(jsonArrayRequest);
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void GetCategoryofProduct() {
        String url = "http://3.110.34.172:8080/api/categories";
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
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}



