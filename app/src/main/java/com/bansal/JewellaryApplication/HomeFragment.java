package com.bansal.JewellaryApplication;

import static android.app.PendingIntent.getActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    ImageSlider ivsta;
CardView cvHIM,cvHER,cvrate;





    AdpterGetALLcategory adpterGetALLcategory;

ImageView ivinstgarm,ivfacebook,ivyoutube,ivprintrest;


    private ViewPager2 viewPager2;
    private ImageSliderAdapter adapter;
    private List<String> imageUrls = new ArrayList<>();

    CardView cvShoplocation;
    Button btnrateus;
     AdpterGifting adpterGifting;
    ProgressBar progressBar;
    String Him="Him",Her="Her";
ImageView ivWhatsapp;
    ProgressBar pbloading;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        int screenWidth = getResources().getDisplayMetrics().widthPixels;



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
       // rvsolematelist=view.findViewById(R.id.rvSolemetlist);
        pojoSokumates = new ArrayList<>();
        ivtestinomialimage=view.findViewById(R.id.ivtestinomialimage);
        imageSlider = view.findViewById(R.id.imagesliderwelcome);
        ivsta=view.findViewById(R.id.isPromisiMgae);
        cvShoplocation=view.findViewById(R.id.cvShopLocation);
        btnrateus=view.findViewById(R.id.btnHomeFragmentrateus);
         progressBar = view.findViewById(R.id.progressBar);
        adpterGifting = new AdpterGifting(pojoGiftings, getActivity());
        cvHIM=view.findViewById(R.id.cvHomeFragmentHIM);
        cvHER=view.findViewById(R.id.cvHomeFragmentHER);
        ivWhatsapp=view.findViewById(R.id.ivwhatsapp);
        pbloading=view.findViewById(R.id.pbProgreddbar);
        cvrate=view.findViewById(R.id.cvraate);


        cvrate.setOnClickListener(v -> {
            // Direct link to Google Maps review section
            String reviewUrl = "https://search.google.com/local/writereview?placeid=ChIJ78nbKCr9DDkRCUdu1MEiAwM&noredir=1";

            // Intent to open the review section in Google Maps
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewUrl));
            intent.setPackage("com.google.android.apps.maps"); // Open specifically in Google Maps

            // Try to start the activity
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Fallback if Google Maps isn't installed
                Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewUrl));
                startActivity(fallbackIntent);
            }
        });

        ivWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "91982031621"; // Use country code without "+" prefix
                String message = "Hello, I have a question regarding the product from your app.";

                try {
                    // Ensure the URL is properly encoded
                    String url = "https://wa.me/" + phoneNumber + "?text=" + Uri.encode(message);
                    Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                    whatsappIntent.setData(Uri.parse(url));
                    whatsappIntent.setPackage("com.whatsapp"); // Explicitly set WhatsApp package

                    v.getContext().startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {
                    // Handle case where WhatsApp is not installed
                    Toast.makeText(v.getContext(), "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    // Handle generic exceptions
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });




        cvHIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HimORHerproduct.class);
                i.putExtra("gender",Him);
                startActivity(i);
            }
        });
        cvHER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),HimORHerproduct.class);
                i.putExtra("gender",Her);
                startActivity(i);
            }
        });




        cvShoplocation.setOnClickListener(v -> {
            // Google Maps URL
            String googleMapsUrl = "https://www.google.com/maps/place/Bansal+%26+sons+Jewellers/@28.6574758,77.2288831,17z/data=!3m1!4b1!4m6!3m5!1s0x390cfd2a28dbc9ef:0x30322c1d46e4709!8m2!3d28.6574758!4d77.2288831!16s%2Fg%2F1pzr0nf9x?entry=ttu";

            // Intent to open Google Maps
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl));
            intent.setPackage("com.google.android.apps.maps"); // Ensure it opens in Google Maps

            // Start the intent
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Fallback if Google Maps isn't installed
                Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapsUrl));
                startActivity(fallbackIntent);
            }
        });




        btnrateus.setOnClickListener(v -> {
            // Direct link to Google Maps review section
            String reviewUrl = "https://search.google.com/local/writereview?placeid=ChIJ78nbKCr9DDkRCUdu1MEiAwM&noredir=1";

            // Intent to open the review section in Google Maps
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewUrl));
            intent.setPackage("com.google.android.apps.maps"); // Open specifically in Google Maps

            // Try to start the activity
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                // Fallback if Google Maps isn't installed
                Intent fallbackIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewUrl));
                startActivity(fallbackIntent);
            }
        });






        ArrayList<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.ps2,ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.ppp,ScaleTypes.CENTER_CROP));
        ivsta.setImageList(slideModels);
        ivsta.setSlideAnimation(AnimationTypes.ZOOM_IN);








        ArrayList<SlideModel> slideModelArrayList = new ArrayList<>();
        slideModelArrayList.add(new SlideModel(R.drawable.img1, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img3, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img5, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img6, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img7, ScaleTypes.CENTER_CROP));
        slideModelArrayList.add(new SlideModel(R.drawable.img8, ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModelArrayList);
        imageSlider.setSlideAnimation(AnimationTypes.ZOOM_IN);











        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3); // 3 items per row
        rvlistofcategory.setLayoutManager(gridLayoutManager);
        rvoccusion.setLayoutManager(new GridLayoutManager(getActivity(),1,GridLayoutManager.HORIZONTAL,false));
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false);
        rvgiftGuid.setLayoutManager(layoutManager);

        rvgiftGuid.setAdapter(adpterGifting);



        rvMakrketpricelist.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));


       // rvsolematelist.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        // Instagram Button
        
        ivinstgarm.setOnClickListener(v -> openUrl("https://www.instagram.com/bansal_and_sons_jewellers?igsh=MWQ5MWJ3dXNmZWlyeA=="));

        // Facebook Button
        
        ivfacebook.setOnClickListener(v -> openUrl("https://www.facebook.com/bansalandsonsjewellerspvtltd?mibextid=ZbWKwL"));

        // Pinterest Button
        
        ivyoutube.setOnClickListener(v -> openUrl("https://www.youtube.com/@bansalsonsjewellers6387"));

        // YouTube Button
        
        ivprintrest.setOnClickListener(v ->
                openUrl("https://pin.it/2ACtK6wmi"));


        GetCategoryofProduct();
        fetchPrices();
        fetchGiftingData();
        fetchBannerImages();
        fetchoccuction();
       // fetchsoulmate();
        fetchTestimonial();


        return view;

    }



    private void fetchGiftingData() {
        String Giftingurl = "https://api.gehnamall.com/api/gifting";
        progressBar.setVisibility(View.VISIBLE);
        rvgiftGuid.setVisibility(View.GONE);

        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());

        // Log the start time
        long startTime = System.currentTimeMillis();
        Log.d("API_CALL", "API call started at: " + startTime);


        // Create JsonArrayRequest for fetching data
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                Giftingurl,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Log the end time
                        long endTime = System.currentTimeMillis();
                        Log.d("API_CALL", "API call completed at: " + endTime);

                        // Calculate the time taken for the API call
                        long timeTaken = endTime - startTime;
                        Log.d("API_CALL", "Time taken for API call: " + timeTaken + " ms");

                        try {
                            pojoGiftings.clear(); // Clear the list to prevent duplicate data

                            // Loop through the response
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject giftingObj = response.getJSONObject(i);

                                // Fetch data using the correct key names
                                String giftingName = giftingObj.getString("giftingName");
                                int giftingCode = giftingObj.getInt("giftingCode");
                                String exfield1 = giftingObj.getString("exfield1");

                                pojoGiftings.add(new POJOGifting(giftingName, giftingCode, exfield1));

                            }


                            // Notify the adapter that the data set has changed
                            adpterGifting.notifyDataSetChanged();

                            // Toggle visibility based on data presence
                            if (pojoGiftings.isEmpty()) {
                                // No data: show ProgressBar
                                progressBar.setVisibility(View.VISIBLE);
                                rvgiftGuid.setVisibility(View.GONE);
                            } else {
                                // Data present: show RecyclerView
                                progressBar.setVisibility(View.GONE);
                                rvgiftGuid.setVisibility(View.VISIBLE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(requireActivity(), "Parsing Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
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
        String url = "https://api.gehnamall.com/api/prices";
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity()); // Initialize the request queue

        pojOgetPrices = new ArrayList<>(); // Initialize the list that will hold your data

        // Set the RecyclerView LayoutManager to a vertical LinearLayoutManager
        rvMakrketpricelist.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false));

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

                                adpterGetPrice = new AdpterGetPrice(pojOgetPrices, requireActivity());
                                rvMakrketpricelist.setAdapter(adpterGetPrice); // Set the RecyclerView adapter


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
        String url = "https://api.gehnamall.com/api/categories?wholeseller=BANSAL";

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
        String url = "https://api.gehnamall.com/api/banners"; // Replace with your actual API URL

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

        autoScrollViewPager();
    }

    private void autoScrollViewPager() {
        // Runnable to change pages
        final Runnable autoScrollRunnable = new Runnable() {
            @Override
            public void run() {
                int nextItem = viewPager2.getCurrentItem() + 1;
                if (nextItem >= imageUrls.size()) {
                    nextItem = 0; // Loop back to the first image if we've reached the end
                }
                viewPager2.setCurrentItem(nextItem, true); // Scroll to the next item
            }
        };

        // Create a handler to run the auto-scrolling every 3 seconds
        Handler handler = new Handler();
        handler.postDelayed(autoScrollRunnable, 6000); // Delay in milliseconds (3000ms = 3 seconds)

        // Optional: Loop the handler to keep it running indefinitely
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                autoScrollViewPager(); // Repeat the auto-scrolling process
            }
        }, 6000);
    }


    // Method to set up ViewPager2 with the adapter
    private void fetchoccuction() {
        String url = "https://api.gehnamall.com/api/occasion";
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
        String url = "https://api.gehnamall.com/api/soulmate";
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
        String url = "https://api.gehnamall.com/api/testimonial";
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




