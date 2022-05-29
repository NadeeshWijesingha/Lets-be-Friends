package com.nadeesh.letsbefriends.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.nadeesh.letsbefriends.R;
import com.nadeesh.letsbefriends.data_adapters.chatAdapter;
import com.nadeesh.letsbefriends.dbHelpers.chatroomsHelper;
import com.nadeesh.letsbefriends.models.chatroom_model;

import java.util.ArrayList;

public class fragment_tab3 extends Fragment {

    chatroomsHelper chatroomsHelper;
    ArrayList<chatroom_model> chatroomList;
    chatAdapter chatAdapter;

    ListView chatroomListview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_tab3, container, false);

        chatroomListview = view.findViewById(R.id.chatroomListview);
        chatroomsHelper =  new chatroomsHelper(getContext());
        chatroomList = new ArrayList<>();
        fetchData();

        return view;
    }
    private void fetchData(){
        chatroomList = chatroomsHelper.getAllChatRooms();
        chatAdapter =  new chatAdapter(getContext(), chatroomList);
        chatroomListview.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            fetchData();
        }
    }
}