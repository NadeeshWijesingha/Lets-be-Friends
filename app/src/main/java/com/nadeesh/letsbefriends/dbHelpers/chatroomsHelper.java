package com.nadeesh.letsbefriends.dbHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nadeesh.letsbefriends.models.chatroom_model;
import com.nadeesh.letsbefriends.models.requests_model;

import java.util.ArrayList;

public class chatroomsHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "main.db";

    public chatroomsHelper(@Nullable Context context) {
        super(context, "main.db", null, 1);
    }




    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        try{
            MyDB.execSQL("create Table chatroom(chatRoom INTEGER primary key, owner TEXT, recipient TEXT, isActive BOOLEAN)");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists chatroom");
    }
    public boolean insertData( String owner, String recipient,  boolean isActive){
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("owner", owner);
        contentValues.put("recipient",recipient);
        contentValues.put("isActive", isActive);
        long result = MyDB.insert("chatroom", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<chatroom_model> getAllChatRooms() {
        ArrayList<chatroom_model> arrayList =  new ArrayList<>();
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * From chatroom", null);

        while(cursor.moveToNext()){

            int chatRoom = cursor.getInt(0);
            String owner = cursor.getString(1);
            String recipient = cursor.getString(2);
            boolean isAccepted = cursor.getInt(3)> 0;

            chatroom_model model_data = new chatroom_model(chatRoom,owner,recipient,isAccepted);

            arrayList.add(model_data);
        }

        return arrayList;
    }
}
