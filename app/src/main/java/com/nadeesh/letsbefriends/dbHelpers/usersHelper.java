package com.nadeesh.letsbefriends.dbHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nadeesh.letsbefriends.models.user_model;

import java.util.ArrayList;

public class usersHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "main.db";

    public usersHelper(@Nullable Context context) {
        super(context, "main.db", null, 1);
    }




    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        try{
            MyDB.execSQL("create Table users(username TEXT primary key,  email TEXT, password TEXT, isAvailable BOOLEAN, active_email TEXT, roomNo INTEGER)");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }
    public boolean insertData(String username,String email, String password){
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("isAvailable", true);
        long result = MyDB.insert("users", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean checkUserName(String username){

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor =  MyDB.rawQuery("Select * from users where username = ?", new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkCredentials(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username =? and password =?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public ArrayList<user_model> getAvailable(String account_username){
        ArrayList<user_model> arrayList =  new ArrayList<>();
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * From users where isAvailable = true AND username != ?", new String[]{account_username});

        while(cursor.moveToNext()){
            String username =  cursor.getString(0);
            String email = cursor.getString(1);
            String password = "";
            boolean isAvailable = cursor.getInt(3) > 0;
            String active_email = cursor.getString(4);
            int roomNo = cursor.getInt(5);

            user_model newModel = new user_model( username, email,  password,  isAvailable, active_email, roomNo);

            arrayList.add(newModel);
        }

        return arrayList;
    }
}
