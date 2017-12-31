package com.example.mohamed.blindapp.appliction;


import com.example.mohamed.blindapp.data.User;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/12/2017.  time :22:12
 */

public class DataManager {
    private MyShared myShared;

    public DataManager(MyShared myShared){
        this.myShared=myShared;
    }

    public void setUser(String name,String phone,String type,String userID){
        myShared.setUser(name,phone,type,userID);
    }

    public User getUser(){
        return myShared.getUser();
    }

    public void clear(){
        myShared.clear();
    }

}
