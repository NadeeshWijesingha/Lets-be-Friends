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

import com.nadeesh.letsbefriends.models.messages_model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class messagesHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "main.db";

    public messagesHelper(@Nullable Context context) {
        super(context, "main.db", null, 1);
    }



    @Override
    public void onOpen(SQLiteDatabase db) {
        onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        try{
            MyDB.execSQL("create Table messages(message_id INTEGER PRIMARY KEY,email TEXT, post_id TEXT, message TEXT,img blob, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP)");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists messages");
    }

    public boolean newComment(String email , String post_id, String message, byte[] img){
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("post_id",post_id);
        contentValues.put("message", message);
        contentValues.put("img", img);
        long result = MyDB.insert("messages", null, contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public ArrayList<messages_model> getAllData(String postId) throws ParseException {
        ArrayList<messages_model> arrayList =  new ArrayList<>();
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * From messages where post_id = ?", new String[]{postId});

        while(cursor.moveToNext()){
            int message_id =  cursor.getInt(0);
            String email = cursor.getString(1);
            String post_id = cursor.getString(2);
            String message = cursor.getString(3);

            byte[] bitmap = cursor.getBlob(4);
            Bitmap img = BitmapFactory.decodeByteArray(bitmap,0, bitmap.length);
            String tS_String = cursor.getString(5);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            Date timeStamp = dateFormat.parse(tS_String);


            messages_model newModel = new messages_model(message_id,email,post_id,message,timeStamp,img);

            arrayList.add(newModel);
        }

        return arrayList;
    }

    public boolean deleteComment(String post_id, String message){
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        long result = MyDB.delete("messages", "post_id = ? and message = ?" ,new String[]{post_id,message});
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean updateComment(String post_id, String message, String newComment){
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("message",newComment);
        long result = MyDB.update("messages", values,"post_id = ? and message = ?" ,new String[]{post_id,message});
        if(result == -1)
            return false;
        else
            return true;
    }
    public ArrayList<messages_model> search(String parameter)throws ParseException{
        ArrayList<messages_model> arrayList =  new ArrayList<>();
        SQLiteDatabase MyDB =  this.getWritableDatabase();
        String new_parameter = "%" + parameter +"%";
        Cursor cursor = MyDB.rawQuery("SELECT * From messages where message like ?", new String []{new_parameter});

        while(cursor.moveToNext()){
            int message_id =  cursor.getInt(0);
            String email = cursor.getString(1);
            String post_id = cursor.getString(2);
            String message = cursor.getString(3);

            byte[] bitmap = cursor.getBlob(4);
            Bitmap img = BitmapFactory.decodeByteArray(bitmap,0, bitmap.length);
            String tS_String = cursor.getString(5);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
            Date timeStamp = dateFormat.parse(tS_String);


            messages_model newModel = new messages_model(message_id,email,post_id,message,timeStamp,img);

            arrayList.add(newModel);
        }

        return arrayList;
    }

}
