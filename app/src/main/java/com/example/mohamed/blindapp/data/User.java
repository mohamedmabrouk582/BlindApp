package com.example.mohamed.blindapp.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 26/12/2017.  time :21:30
 */

public class User implements Parcelable {
    private String name;
    private String phone;
    private String type;
    private String userid;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +

                '}';
    }



    public User() {
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.type);
        dest.writeString(this.userid);
    }

    protected User(Parcel in) {
        this.name = in.readString();
        this.phone = in.readString();
        this.type = in.readString();
        this.userid = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
