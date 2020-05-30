package com.example.multicast;

public class UserGroup {
    String userId, groupId;

    public UserGroup(){
        //get database
    }

    public UserGroup(String groupId, String userId) {
        this.userId = userId;
        this.groupId = groupId;
    }

    public String getUserid() {
        return userId;
    }

    public void setUserid(String userid) {
        this.userId = userId;
    }

    public String getGroupid() {
        return groupId;
    }

    public void setGroupid(String groupid) {
        this.groupId = groupId;
    }
}
