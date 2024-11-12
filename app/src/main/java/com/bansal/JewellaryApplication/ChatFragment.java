package com.bansal.JewellaryApplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class ChatFragment extends Fragment {

   AppCompatButton btnWhatsapp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
      btnWhatsapp = view.findViewById(R.id.ChatFragmentwhatsappbutton);
        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+919717223601"; // Indian phone number format
                String message = "Hello, I have a question regarding the product from our app.";


                String url = "https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(message);

                Intent whatsappIntent = new Intent(Intent.ACTION_VIEW);
                whatsappIntent.setData(Uri.parse(url));

                try {

                    startActivity(whatsappIntent);
                } catch (ActivityNotFoundException e) {

                    Toast.makeText(getActivity(), "WhatsApp is not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  view;
    }
}