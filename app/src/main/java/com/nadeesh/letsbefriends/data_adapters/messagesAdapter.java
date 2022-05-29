package com.nadeesh.letsbefriends.data_adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.nadeesh.letsbefriends.R;
import com.nadeesh.letsbefriends.models.messages_model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class messagesAdapter extends BaseAdapter {
    Context context;
    ArrayList<messages_model> arrayList;
    SharedPreferences sharedPreferences;

    public messagesAdapter(Context context, ArrayList<messages_model> arrayList){
        this.context= context;
        this.arrayList= arrayList;
        sharedPreferences = context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
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


        String email = sharedPreferences.getString("username", "");



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =  inflater.inflate(R.layout.message_list_view, null);
        TextView message_body = (TextView)view.findViewById(R.id.post_title);
        TextView message_time = (TextView)view.findViewById(R.id.post_description);
        ImageView message_image = (ImageView) view.findViewById(R.id.message_image);

        messages_model messages_model = arrayList.get(i);


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.US);

        String time = df.format(messages_model.getTimeStamp());
        message_body.setText(messages_model.getComment());
        message_time.setText(time);
        Bitmap bitmap = messages_model.getImg();
        if(bitmap == null){
            //message_image.setVisibility(View.GONE);
            message_image.setImageBitmap(messages_model.getImg());
        }else {
            message_image.setImageBitmap(messages_model.getImg());
        }
        if(messages_model.getEmail().equals(email)){
            message_body.setGravity(Gravity.RIGHT);
            message_time.setGravity(Gravity.RIGHT);
            message_body.setTextColor(Color.BLUE);
        }else{
            message_body.setGravity(Gravity.LEFT);
            message_time.setGravity(Gravity.LEFT);
            message_body.setTextColor(Color.RED);
        }

        return view;
    }
}
