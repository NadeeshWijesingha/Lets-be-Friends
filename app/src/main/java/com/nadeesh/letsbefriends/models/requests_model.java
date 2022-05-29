package com.nadeesh.letsbefriends.models;

public class requests_model {

    String owner;
    String recipient;
    boolean isAccepted;

    public requests_model(String owner, String recipient, boolean isAccepted) {
        this.owner = owner;
        this.recipient = recipient;
        this.isAccepted = isAccepted;
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

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }
}
