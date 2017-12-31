package com.example.mohamed.blindapp.presenter.register;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.presenter.base.BasePresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.view.RegisterView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :17:46
 */

public class RegisterViewPresenter<v extends RegisterView> extends BasePresenter<v> implements RegisterPresenter<v> {
    private Activity activity;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private DataManager dataManager;

    public RegisterViewPresenter(Activity activity){
        this.activity=activity;
        mAuth= MyApp.getAuth();
        mDatabaseReference=MyApp.getDatabaseReference().child("Users");
        dataManager=((MyApp) activity.getApplication()).getDataManager();

    }

    @Override
    public void register(final String userName, final String email, String password, final String type,final String userId, final AddListener listener) {
       mAuth.createUserWithEmailAndPassword(email+"@gmail.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){

                   Map<String,String > map=new HashMap();
                   map.put("name",userName);
                   map.put("phone",email);
                   map.put("type",type);
                   map.put("userid",userId);
                   mDatabaseReference.child(mAuth.getUid()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                       @Override
                       public void onSuccess(Void aVoid) {
                           dataManager.clear();
                           dataManager.setUser(userName,email,type,userId);
                          listener.onSuccess("");
                       }
                   });
               }else {
                   Log.d("eeee", "" + "");
                   listener.onError(task.getException().getMessage());
               }
           }
       });
    }
}
