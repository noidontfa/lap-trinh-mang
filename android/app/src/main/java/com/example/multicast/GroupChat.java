package com.example.multicast;

public class GroupChat {
    private String name;
    private String description;
    private int image;
    private boolean state;

    public GroupChat(String name, String description, boolean state, int image) {
        this.name = name;
        this.description = description;
        this.state = state;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
