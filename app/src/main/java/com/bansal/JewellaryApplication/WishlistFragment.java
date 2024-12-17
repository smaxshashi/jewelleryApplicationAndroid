package com.bansal.JewellaryApplication;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bansal.JewellaryApplication.Adapterclasses.WishlistAdapter;
import com.bansal.JewellaryApplication.pojoclasses.PojoAddtowishlist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.List;


public class WishlistFragment extends Fragment {

   RecyclerView rvListofproduct;
   String UserId;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wishlist, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        UserId=sharedPreferences.getString("userId","");
        rvListofproduct=view.findViewById(R.id.wishlistRecyclerView);
        rvListofproduct.setLayoutManager(new GridLayoutManager(getActivity(),2,GridLayoutManager.VERTICAL,false));
        fetchCartItems(UserId);

        return view;
    }

    private void fetchCartItems(String userId) {
        String url = "https://api.gehnamall.com/api/getCartItem?userId=" + userId;

        // Fetch XML response as a String using Volley
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<PojoAddtowishlist> cartItems = new ArrayList<>();

                            // Convert the response from XML to JSON (manual parsing here for simulation)
                            JSONArray productsArray = new JSONObject(response).getJSONArray("enhancedProducts");

                            for (int i = 0; i < productsArray.length(); i++) {
                                JSONObject productObj = productsArray.getJSONObject(i);

                                // Extract required fields
                                String productId = productObj.optString("productId", "N/A");
                                String productName = productObj.optString("productName", "N/A");
                                String weight = productObj.optString("weight", "N/A");
                                String karat = productObj.optString("karat", "N/A");

                                // Extract image URLs (assume first image for display)
                                JSONArray imageUrlArray = productObj.getJSONArray("imageUrls");
                                String imageUrl = imageUrlArray.length() > 0 ? imageUrlArray.getString(0) : "";

                                // Add product to list
                                cartItems.add(new PojoAddtowishlist(productId, productName, weight, karat, imageUrl));
                            }

                            // Pass the list to RecyclerView Adapter
                            WishlistAdapter adapter = new WishlistAdapter(getActivity(), cartItems);
                            rvListofproduct.setAdapter(adapter);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e("JSON_ERROR", "Error parsing JSON: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley_Error", "Error fetching data: " + error.getMessage());
                    }
                });

        // Add the request to the Volley queue
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }



}