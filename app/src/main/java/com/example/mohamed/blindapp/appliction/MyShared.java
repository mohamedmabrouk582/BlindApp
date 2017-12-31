package com.example.mohamed.blindapp.appliction;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.mohamed.blindapp.data.User;


/**
 * Created by mohamed mabrouk
 *
 * 0201152644726
 * on 27/12/2017.  time :22:05
 */

public class MyShared {
    private static final String USERID = "userid";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    public static final String NAME="NAME";
    public static final String PHONE="PHONE";
    public static final String TYPE="TYPE";

    public MyShared(Context activity){
        mSharedPreferences=activity.getSharedPreferences("users", Context.MODE_PRIVATE);
        mEditor=mSharedPreferences.edit();
    }

    public void setUser(String name ,String phone,String type,String userId){
        mEditor.clear();
        mEditor.putString(NAME,name);
        mEditor.putString(PHONE,phone);
        mEditor.putString(TYPE,type);
        mEditor.putString(USERID,userId);
        mEditor.apply();
    }

    public User getUser(){
        Log.d("type", mSharedPreferences.getString(TYPE,null) + "");
        User user=new User();
        user.setName(mSharedPreferences.getString(NAME,null));
        user.setPhone(mSharedPreferences.getString(PHONE,null));
        user.setType(mSharedPreferences.getString(TYPE,null));
        user.setUserid(mSharedPreferences.getString(USERID,null));
        return user;
    }

    public void clear() {
        mEditor.clear();
    }
}
