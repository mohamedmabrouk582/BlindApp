package com.example.mohamed.blindapp.appliction;

import android.app.Application;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/12/2017.  time :21:47
 */

public class MyApp extends Application{
     private static  DataManager dataManager;
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .autoPromptLocation(true)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .init();
        MyShared myShared=new MyShared(this);
        dataManager=new DataManager(myShared);
    }

    public static DataManager getDataManager(){
        return dataManager;
    }

    public static FirebaseAuth getAuth(){
        return FirebaseAuth.getInstance();
    }

    public static DatabaseReference getDatabaseReference(){
        return FirebaseDatabase.getInstance().getReference();
    }

}
