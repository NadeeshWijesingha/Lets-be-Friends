package com.nadeesh.letsbefriends.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nadeesh.letsbefriends.R;
import com.nadeesh.letsbefriends.dbHelpers.requestsHelper;
import com.nadeesh.letsbefriends.dbHelpers.usersHelper;
import com.nadeesh.letsbefriends.models.user_model;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class fragment_tab1 extends Fragment {

    Button searchBtn, cardSend, cardCancel;
    usersHelper user_database;
    CardView searchCard;
    ArrayList<user_model> usersList;
    TextView introText, cardName, noUsers;
    requestsHelper requestsHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tab1, container, false);

        searchBtn= view.findViewById(R.id.searchBtn);
        cardSend = view.findViewById(R.id.cardSend);
        cardCancel = view.findViewById(R.id.cardCancel);
        searchCard = view.findViewById(R.id.searchCard);
        user_database = new usersHelper(getContext());
        introText = view.findViewById(R.id.introText);
        cardName = view.findViewById(R.id.cardName);
        noUsers = view.findViewById(R.id.noUsers);

        requestsHelper = new requestsHelper(getContext());

        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String loggedEmail = sh.getString("username","");
        usersList =  user_database.getAvailable(loggedEmail);
        Random rand = new Random();
        AtomicInteger selectedNumber = new AtomicInteger(1);
        if(usersList.size() == 0){
            searchBtn.setVisibility(View.GONE);
            noUsers.setVisibility(View.VISIBLE);
        }

        introText.setText("Welcome " + loggedEmail + "............!");

        searchBtn.setOnClickListener(view1 -> {
            int randomNum = rand.nextInt((usersList.size() - 1) + 1) + 1;
            selectedNumber.set(randomNum);
            Log.d("Random Number",String.valueOf(randomNum));
            searchBtn.setVisibility(View.GONE);
            searchCard.setVisibility(View.VISIBLE);
            cardSend.setVisibility(View.VISIBLE);
            cardName.setText(usersList.get(randomNum-1).getUsername());
            Log.d("Count", String.valueOf(usersList.size()));
        });

        cardCancel.setOnClickListener(view1 -> {
            searchCard.setVisibility(View.GONE);
            searchBtn.setVisibility(View.VISIBLE);

        });

        cardSend.setOnClickListener(view1 -> {
            cardName.setText("Request Sent : " + usersList.get(selectedNumber.get() -1).getUsername());
            cardSend.setVisibility(View.GONE);


            boolean makeRequest = requestsHelper.newRequest(loggedEmail,usersList.get(selectedNumber.get() -1).getUsername(), false);

            if(makeRequest){
                Toast.makeText(getContext(), "Request Sent", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), "Request Send Failed", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

}