package com.nadeesh.letsbefriends.models;

public class chatroom_model {
    int chatRoom;
    String owner;
    String recipient;
    boolean isActive;

    public chatroom_model(int chatRoom, String owner, String recipient, boolean isActive){
        this.chatRoom = chatRoom;
        this.owner = owner;
        this.recipient = recipient;
        this.isActive= isActive;
    }

    public int getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(int chatRoom) {
        this.chatRoom = chatRoom;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
