package com.example.mohamed.blindapp.presenter.login;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.mohamed.blindapp.activity.HomeActivity;
import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.data.User;
import com.example.mohamed.blindapp.presenter.base.BasePresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.view.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 26/12/2017.  time :21:42
 */

public class LoginViewPresenter<v extends LoginView> extends BasePresenter<v> implements LoginPresenter<v> {
    private Activity activity;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;
    private DataManager dataManager;
    public LoginViewPresenter(Activity activity){
        this.activity=activity;
        mAuth= MyApp.getAuth();
        mDatabaseReference=MyApp.getDatabaseReference().child("Users");
        dataManager=((MyApp) activity.getApplication()).getDataManager();
    }
    @Override
    public void login(String email, String password, final AddListener listener) {
     mAuth.signInWithEmailAndPassword(email+"@gmail.com",password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()){
                 mDatabaseReference.child(mAuth.getUid()).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(DataSnapshot dataSnapshot) {
                         User user=dataSnapshot.getValue(User.class);
                         Log.d("myUser", user + "");
                         dataManager.setUser(user.getName(),user.getPhone(),user.getType(),user.getUserid());
                         listener.onSuccess("");
                     }

                     @Override
                     public void onCancelled(DatabaseError databaseError) {
                       listener.onError(databaseError.getMessage());
                     }
                 });
             }else {
                 listener.onError(task.getException().getMessage());
             }
         }
     });
    }
}
