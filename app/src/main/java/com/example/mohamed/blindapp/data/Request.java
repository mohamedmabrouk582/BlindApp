package com.example.mohamed.blindapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :00:03
 */

public class Request implements Parcelable {
    private String id;
    private String body;
    private String location;
    private String phone;
    private String name;
    private String userid;

    public String getId() {
        return id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.body);
        dest.writeString(this.location);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeString(this.userid);
    }

    public Request() {
    }

    protected Request(Parcel in) {
        this.id = in.readString();
        this.body = in.readString();
        this.location = in.readString();
        this.phone = in.readString();
        this.name = in.readString();
        this.userid = in.readString();
    }

    public static final Parcelable.Creator<Request> CREATOR = new Parcelable.Creator<Request>() {
        @Override
        public Request createFromParcel(Parcel source) {
            return new Request(source);
        }

        @Override
        public Request[] newArray(int size) {
            return new Request[size];
        }
    };
}
