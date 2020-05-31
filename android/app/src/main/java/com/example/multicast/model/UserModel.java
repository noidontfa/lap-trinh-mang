package com.example.multicast.model;

public class UserModel {
    public String id;
    public String name;
    public String username;
    public String password;

    public UserModel() {
    }

    public UserModel(String id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
