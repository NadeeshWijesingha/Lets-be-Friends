package com.nadeesh.letsbefriends.data_adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nadeesh.letsbefriends.R;
import com.nadeesh.letsbefriends.dbHelpers.chatroomsHelper;
import com.nadeesh.letsbefriends.dbHelpers.requestsHelper;
import com.nadeesh.letsbefriends.models.chatroom_model;
import com.nadeesh.letsbefriends.models.requests_model;

import java.util.ArrayList;

public class requestsAdapter extends BaseAdapter {

    Context context;
    ArrayList<requests_model> arrayList;

    requestsHelper requestsHelper;
    chatroomsHelper chatroomsHelper;

    public requestsAdapter(Context context, ArrayList<requests_model> arrayList) {
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
        view =  inflater.inflate(R.layout.requests_list, null);

        TextView cardName =  (TextView) view.findViewById(R.id.cardName);
        Button cardSend = (Button) view.findViewById(R.id.cardSend);
        Button cardCancel = (Button) view.findViewById(R.id.cardCancel);

        requestsHelper = new requestsHelper(context);
        chatroomsHelper = new chatroomsHelper(context);
        requests_model requests_model = arrayList.get(i);

        cardName.setText(requests_model.getOwner());

        cardSend.setOnClickListener(view1 -> {
            boolean addChatroom = chatroomsHelper.insertData(requests_model.getOwner(),requests_model.getRecipient(), true);

            if(addChatroom){
                Toast.makeText(context, "Request Accepted", Toast.LENGTH_SHORT).show();
                boolean deleteRequest =  requestsHelper.deleteRequest(requests_model.getOwner(), requests_model.getRecipient());
                Log.d("", String.valueOf(deleteRequest));

            }else{
                Toast.makeText(context, "Failed to Accept", Toast.LENGTH_SHORT).show();
            }
        });

        cardCancel.setOnClickListener(view1 -> {
            boolean deleteRequest = requestsHelper.deleteRequest(requests_model.getOwner(), requests_model.getRecipient());
            if(deleteRequest){
                Toast.makeText(context, "Request Successfully Cancelled", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "Request Cancelled Failed", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
