package com.nadeesh.letsbefriends.dbHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.nadeesh.letsbefriends.models.requests_model;

import java.util.ArrayList;

public class requestsHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "main.db";

    public requestsHelper(@Nullable Context context) {
        super(context, "main.db", null, 1);
    }




    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        try{
            MyDB.execSQL("create Table requests(owner TEXT ,  recipient TEXT primary key, isAccepted BOOLEAN)");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists requests");
    }
    public boolean newRequest(String owner, String recipient, boolean isAccepted){

        SQLiteDatabase MyDB =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("owner", owner);
        contentValues.put("recipient", recipient);
        contentValues.put("isAccepted", isAccepted);
        long result = MyDB.insert("requests", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<requests_model> getAllRequests(String recipient1){
        ArrayList<requests_model> arrayList =  new ArrayList<>();
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * From requests where recipient = ?",new  String[]{recipient1});

        while(cursor.moveToNext()){

            String owner = cursor.getString(0);
            String recipient = cursor.getString(1);
            boolean isAccepted = cursor.getInt(2)> 0;

            requests_model model_data = new requests_model(owner,recipient,isAccepted);

            arrayList.add(model_data);
        }

        return arrayList;
    }

    public boolean deleteRequest(String owner, String recipient){
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        long result = MyDB.delete("requests", "owner = ? and recipient = ?" ,new String[]{owner, recipient});
        if(result == -1)
            return false;
        else
            return true;
    }

}


