package com.example.mohamed.blindapp.utils;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 06/12/2017.  time :16:08
 */

public class Result {
    private static  Result ourInstance ;
   public interface resultListener {
        void onsuccess(String s);
    }
    private resultListener listener;
    public static Result getInstance() {
        if (ourInstance==null)
            ourInstance=new Result();
        return ourInstance;
    }

    private Result() {
    }

    public void setlistener(resultListener listener){
        if (listener!=null)
            this.listener=listener;
    }

    public void setmsg(String phone){
        listener.onsuccess(phone);
    }
}
