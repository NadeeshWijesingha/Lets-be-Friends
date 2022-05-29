package com.nadeesh.letsbefriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import com.nadeesh.letsbefriends.data_adapters.messagesAdapter;
import com.nadeesh.letsbefriends.dbHelpers.messagesHelper;
import com.nadeesh.letsbefriends.models.messages_model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class Search extends AppCompatActivity {
    ListView postListSearch;
    messagesHelper DB;

    ArrayList<messages_model> arrayList;

    messagesAdapter adapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor myEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String data = intent.getStringExtra("searched");

        postListSearch = findViewById(R.id.postListSearch);



        DB = new messagesHelper(this);

        arrayList = new ArrayList<>();

        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        myEdit = sharedPreferences.edit();

        try {
            loadData(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void loadData(String data) throws ParseException {

        arrayList = DB.search(data);
        adapter = new messagesAdapter(this,arrayList);
        Collections.reverse(arrayList);
        postListSearch.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}