package com.example.multicast.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

public class GroupModel implements Parcelable {
    public String id;
    public String name;
    public String ip;
    public Boolean isJoined= false;

    public GroupModel(String id, String name, String ip) {
        this.id = id;
        this.name = name;
        this.ip = ip;
    }
    public GroupModel(HashMap<String,String> map) {
        this.id = map.get("id");
        this.name = map.get("name");
        this.ip = map.get("ip");
    }

    protected GroupModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        ip = in.readString();
        byte tmpIsJoined = in.readByte();
        isJoined = tmpIsJoined == 0 ? null : tmpIsJoined == 1;
    }

    public static final Creator<GroupModel> CREATOR = new Creator<GroupModel>() {
        @Override
        public GroupModel createFromParcel(Parcel in) {
            return new GroupModel(in);
        }

        @Override
        public GroupModel[] newArray(int size) {
            return new GroupModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(ip);
    }
}
