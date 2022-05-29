package com.nadeesh.letsbefriends.models;

import android.graphics.Bitmap;

import java.util.Date;

public class messages_model {

    int message_id;
    String email;
    String post_id;
    String message;
    Date timeStamp;
    Bitmap img;



    public messages_model(int message_id, String email, String post_id, String message, Date timeStamp, Bitmap img){

        this.message_id = message_id;
        this.email =email;
        this.post_id = post_id;
        this.message = message;
        this.timeStamp =timeStamp;
        this.img = img;

    }

    public messages_model(){}

    public int getComment_id() {
        return message_id;
    }

    public void setComment_id(int message_id) {
        this.message_id = message_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getComment() {
        return message;
    }

    public void setComment(String message) {
        this.message = message;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap img) {
        this.img = img;
    }
}
