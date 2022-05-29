package com.nadeesh.letsbefriends.models;

public class user_model {
    String username;
    String email;
    String password;
    boolean isAvailable;
    String active_email;
    int roomNo;

    public user_model(String username, String email, String password, boolean isAvailable, String active_email, int roomNo){
        this.username = username;
        this.email = email;
        this.password = password;
        this.isAvailable = isAvailable;
        this.active_email = active_email;
        this.roomNo = roomNo;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getActive_email() {
        return active_email;
    }

    public void setActive_email(String active_email) {
        this.active_email = active_email;
    }

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }
}
