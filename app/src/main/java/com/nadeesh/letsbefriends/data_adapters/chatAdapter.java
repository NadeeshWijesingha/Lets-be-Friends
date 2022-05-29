package com.nadeesh.letsbefriends.data_adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nadeesh.letsbefriends.Chat;
import com.nadeesh.letsbefriends.R;
import com.nadeesh.letsbefriends.models.chatroom_model;

import java.util.ArrayList;

public class chatAdapter extends BaseAdapter {

    Context context;
    ArrayList<chatroom_model> arrayList;

    public chatAdapter(Context context, ArrayList<chatroom_model> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return this.arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =  inflater.inflate(R.layout.chat_list, null);

        SharedPreferences sh = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        String myUsername = sh.getString("username","");

        TextView cardName = (TextView) view.findViewById(R.id.cardName);
        Button viewButton = (Button) view.findViewById(R.id.viewButton);

        chatroom_model chatroomModel = arrayList.get(i);
        int chat_room =  chatroomModel.getChatRoom();



        if(myUsername.equals(chatroomModel.getOwner())){
            cardName.setText(chatroomModel.getRecipient());
        }else {
            cardName.setText(chatroomModel.getOwner());
        }

        viewButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, Chat.class);
            intent.putExtra("chat_room", String.valueOf(chatroomModel.getChatRoom()));
            //intent.putExtra("chat_recipient", chatroomModel.getRecipient());
            context.startActivity(intent);

            Log.d("",(String.valueOf(chatroomModel.getChatRoom())));
        });

        return view;
    }
}
