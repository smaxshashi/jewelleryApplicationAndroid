package com.bansal.JewellaryApplication;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGetALLcategory;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGetPrice;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterGifting;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterOccusion;
import com.bansal.JewellaryApplication.Adapterclasses.AdpterSoulmate;
import com.bansal.JewellaryApplication.Adapterclasses.ImageSliderAdapter;
import com.bansal.JewellaryApplication.pojoclasses.POJOGetallcategory;
import com.bansal.JewellaryApplication.pojoclasses.POJOGifting;
import com.bansal.JewellaryApplication.pojoclasses.POJOOccusion;
import com.bansal.JewellaryApplication.pojoclasses.POJOSokumate;
import com.bansal.JewellaryApplication.pojoclasses.POJOgetPrice;
import com.bumptech.glide.Glide;
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


RecyclerView rvlistofcategory,rvMakrketpricelist,rvgiftGuid,rvoccusion,rvsolematelist;
List<POJOGetallcategory> pojoGetallcategories;
List<POJOgetPrice> pojOgetPrices;
List<POJOGifting> pojoGiftings;
List<POJOOccusion> pojoOccusions;
List<POJOSokumate> pojoSokumates;
AdpterGetPrice adpterGetPrice;
ImageView ivtestinomialimage;
    ImageSlider imageSlider;





    AdpterGetALLcategory adpterGetALLcategory;

ImageView ivinstgarm,ivfacebook,ivyoutube,ivprintrest;


    private ViewPager2 viewPager2;
    private ImageSliderAdapter adapter;
    private List<String> imageUrls = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);


        rvlistofcategory = view.findViewById(R.id.rvUserHomeFragmentcategorylist);
        rvMakrketpricelist = view.findViewById(R.id.rvUserHomeFragmentListmaket);
        ivinstgarm = view.findViewById(R.id.ivinstgarm);
        ivfacebook = view.findViewById(R.id.ivFacebookk);
        ivyoutube = view.findViewById(R.id.ivYoutube);
        ivprintrest = view.findViewById(R.id.ivPrintrest);
        rvgiftGuid=view.findViewById(R.id.rvUserHomeFragmentGiftingguidelist);
        pojoGetallcategories =  new ArrayList<>();
        pojoGiftings = new ArrayList<>();
        pojoOccusions = new ArrayList<>();
        viewPager2 = view.findViewById(R.id.viewPager);
        rvoccusion=view.findViewById(R.id.rvHomefargemtshopbyoccusion);
        rvsolematelist=view.findViewById(R.id.rvSolemetlist);
        pojoSokumates = new ArrayList<>();
        ivtestinomialimage=view.findViewById(R.id.ivtestinomialimage);
        imageSlider = view.findViewById(R.id.imagesliderwelcome);

        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.img1, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img3, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img4, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img5, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img6, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img7, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img8, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_IN);










        rvlistofcategory.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));
        rvoccusion.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));
        rvgiftGuid.setLayoutManager(new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false));
        rvMakrketpricelist.setLayoutManager(new GridLayoutManager(getActivity(),1,GridLayoutManager.VERTICAL,false));
        rvsolematelist.setLayoutManager(new GridLayoutManager(getActivity(), 2));


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
        fetchPrices();
        fetchGiftingData();
        fetchBannerImages();
        fetchoccuction();
        fetchsoulmate();
        fetchTestimonial();


        return view;

    }



    private void fetchGiftingData() {
        String url = "http://3.110.34.172:8080/api/gifting";
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());

        // Initialize the list

        // Create JsonArrayRequest for fetching data
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            pojoGiftings.clear(); // Clear the list to prevent duplicate data

                            // Loop through the response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject giftingObj = response.getJSONObject(i);

                                // Fetch data using the correct key names
                                String giftingName = giftingObj.getString("giftingName");
                                int giftingCode = giftingObj.getInt("giftingCode");
                                String exfield1 = giftingObj.getString("exfield1");

                                // Create POJOGifting object and add to the list
                                pojoGiftings.add(new POJOGifting(giftingName, giftingCode, exfield1));
                            }

                            // Update adapter
                            AdpterGifting adpterGifting = new AdpterGifting(pojoGiftings, getActivity());
                            rvgiftGuid.setAdapter(adpterGifting);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireActivity(), "Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }


    private void fetchPrices() {
        String url = "http://3.110.34.172:8080/api/prices";
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity()); // Initialize the request queue

        pojOgetPrices = new ArrayList<>(); // Initialize the list that will hold your data

        // JSON Array request to fetch the data
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET, // HTTP GET request
                url, // The URL of your API
                null, // No request body
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            pojOgetPrices.clear(); // Clear previous data to prevent duplication

                            // Iterate through each element in the response array
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject categoryObj = response.getJSONObject(i); // Get each element as a JSONObject
                                Log.d("API categoryObj", categoryObj.toString()); // Log the object for debugging

                                // Get the metalName
                                String strMetalName = categoryObj.optString("metalName", "Unknown");

                                // Get the price values by using the keys "18K:", "14K:", "24K:", "22K:"
                                String str18K = categoryObj.optString("18K:", "0.0");
                                String str14K = categoryObj.optString("14K:", "0.0");
                                String str24K = categoryObj.optString("24K:", "0.0");
                                String str22K = categoryObj.optString("22K:", "0.0");

                                // Add the parsed data into your list
                                pojOgetPrices.add(new POJOgetPrice(str24K, str22K, str18K, str14K, strMetalName));
                            }

                            // Update the adapter with the new list of prices
                            if (adpterGetPrice == null) {
                                adpterGetPrice = new AdpterGetPrice(pojOgetPrices, requireActivity());
                                rvMakrketpricelist.setAdapter(adpterGetPrice); // Set the RecyclerView adapter
                            } else {
                                adpterGetPrice.notifyDataSetChanged(); // Notify the adapter to update the UI
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireActivity(), "Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Add the request to the queue
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

    private void fetchBannerImages() {
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = "http://3.110.34.172:8080/api/banners"; // Replace with your actual API URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    // Clear any previous images from the list before adding new ones
                    imageUrls.clear();

                    // Loop through the response and extract image URLs
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject bannerObject = response.getJSONObject(i);
                            String imageUrl = bannerObject.getString("imageUrl");
                            Log.d("API Response", "Image URL: " + imageUrl);  // Log the image URL for debugging
                            imageUrls.add(imageUrl);  // Add image URL to the list
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("JSON Error", "Error parsing JSON response", e);
                        }
                    }

                    // Log the size of imageUrls list before using it
                    Log.d("Image URLs", "List size: " + imageUrls.size());

                    // If imageUrls list is empty, log a warning
                    if (imageUrls.isEmpty()) {
                        Log.w("API Response", "No image URLs found.");
                    }

                    // Set up ViewPager2 with the fetched image URLs
                    setupImageSlider();
                },
                error -> {
                    if (error.networkResponse != null) {
                        int statusCode = error.networkResponse.statusCode;
                        String errorMessage = new String(error.networkResponse.data);
                        Log.e("Volley Error", "Error fetching data: " + statusCode + " " + errorMessage);
                        Toast.makeText(getActivity(), "Error " + statusCode + ": " + errorMessage, Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("Volley Error", "Error: " + error.getMessage());
                        Toast.makeText(getActivity(), "Error fetching images: " + error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.e("Volley Error", "Stack Trace:", error);
                    }
                }) {
            @Override
            public RetryPolicy getRetryPolicy() {
                return new DefaultRetryPolicy(
                        5000,  // 5 seconds timeout
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            }
        };

        // Add the request to the request queue
        queue.add(jsonArrayRequest);
    }



    private void setupImageSlider() {
        // Set up the image slider (ViewPager2)
        ImageSliderAdapter adapter = new ImageSliderAdapter(getActivity(), imageUrls);
        viewPager2.setAdapter(adapter);
    }

    // Method to set up ViewPager2 with the adapter
    private void fetchoccuction() {
        String url = "http://3.110.34.172:8080/api/occasion";
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());

        // Initialize the list

        // Create JsonArrayRequest for fetching data
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            pojoGiftings.clear(); // Clear the list to prevent duplicate data

                            // Loop through the response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject giftingObj = response.getJSONObject(i);

                                // Fetch data using the correct key names
                                String giftingName = giftingObj.getString("giftingName");
                                int giftingCode = giftingObj.getInt("giftingCode");
                                String exfield1 = giftingObj.getString("exfield1");
                                pojoOccusions.add(new POJOOccusion(giftingName,exfield1,giftingCode));

                                // Create POJOGifting object and add to the list

                            }

                            // Update adapter
                            AdpterOccusion adpterOccusion = new AdpterOccusion(pojoOccusions,getActivity());
                            rvoccusion.setAdapter(adpterOccusion);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireActivity(), "Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(requireActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }



    private void fetchsoulmate()
    {
        String url = "http://3.110.34.172:8080/api/soulmate";
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
                                String name = categoryObj.getString("giftingName");
                                String code = categoryObj.getString("giftingCode");
                                String image = categoryObj.getString("exfield1");
                                pojoSokumates.add(new POJOSokumate(name,code,image));




                            }
                            AdpterSoulmate adpterSoulmate = new AdpterSoulmate(pojoSokumates,getActivity());
                            rvsolematelist.setAdapter(adpterSoulmate);
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


    private void fetchTestimonial() {
        String url = "http://3.110.34.172:8080/api/testimonial";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            // Assuming you are displaying only one image for now
                            if (response.length() > 0) {
                                JSONObject categoryObj = response.getJSONObject(0); // Fetch the first object
                                String image = categoryObj.getString("imageUrl");

                                // Load the image using Glide
                                Glide.with(getActivity())
                                        .load(image)
                                        .error(R.drawable.noimage) // Error placeholder
                                        .into(ivtestinomialimage); // Your ImageView
                            }
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

        // Add the request to the queue
        requestQueue.add(jsonArrayRequest);
    }






























}




