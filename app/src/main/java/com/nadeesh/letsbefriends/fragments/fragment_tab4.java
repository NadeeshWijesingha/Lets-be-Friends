package com.nadeesh.letsbefriends.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nadeesh.letsbefriends.Login;
import com.nadeesh.letsbefriends.R;

public class fragment_tab4 extends Fragment {

    Button logoutBtn;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);

        logoutBtn = view.findViewById(R.id.logoutBtn);
        sharedPreferences = getContext().getSharedPreferences("MySharedPref",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        logoutBtn.setOnClickListener(view1->{
            Intent intent;
            myEdit.putBoolean("isLogged", false);
            myEdit.commit();
            intent = new Intent(getActivity(), Login.class);
            getActivity().startActivity(intent);
            getActivity().finish();
        });

        return view;
    }
}