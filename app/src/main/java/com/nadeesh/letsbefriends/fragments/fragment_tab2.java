package com.nadeesh.letsbefriends.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nadeesh.letsbefriends.R;
import com.nadeesh.letsbefriends.data_adapters.requestsAdapter;
import com.nadeesh.letsbefriends.dbHelpers.requestsHelper;
import com.nadeesh.letsbefriends.models.requests_model;

import java.util.ArrayList;

public class fragment_tab2 extends Fragment {

    requestsHelper requestsHelper;
    ArrayList<requests_model> requestsList;
    requestsAdapter requestsAdapter;
    String loggedEmail;
    ListView requestsListView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_tab2, container, false);

        requestsHelper = new requestsHelper(getContext());
        requestsList = new ArrayList<>();
        requestsListView = view.findViewById(R.id.requestsList);
        SharedPreferences sh = getContext().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        loggedEmail = sh.getString("username","");
        fetchData(loggedEmail);

        return view;
    }
    public void fetchData(String recipient){
        requestsList =  requestsHelper.getAllRequests(recipient);
        requestsAdapter = new requestsAdapter(getContext(), requestsList);
        requestsListView.setAdapter(requestsAdapter);
        requestsAdapter.notifyDataSetChanged();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            fetchData(loggedEmail);
        }
    }
}