package com.example.multicast;

import java.io.Serializable;

public class Message implements Serializable {
    private String id="";
    private String message="";
    private byte[] image;

    public Message(String id, String message, byte[] image) {
        this.id = id;
        this.message = message;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
