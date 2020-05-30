package com.example.multicast;

public class GroupChat {
    private String id, ip, name;

    public GroupChat(){

    }

    public GroupChat(String id, String ip, String name) {
        this.id = id;
        this.ip = ip;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
