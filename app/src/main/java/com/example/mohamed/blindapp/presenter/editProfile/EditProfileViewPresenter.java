package com.example.mohamed.blindapp.presenter.editProfile;

import android.app.Activity;

import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.presenter.base.BasePresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.view.EditProfileView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :10:07
 */

public class EditProfileViewPresenter<v extends EditProfileView> extends BasePresenter<v> implements EditProfilePresenter<v> {
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private Activity activity;
    private DataManager dataManager;


    public EditProfileViewPresenter(Activity activity){
        this.activity=activity;
        mAuth= MyApp.getAuth();
        mDatabaseReference=MyApp.getDatabaseReference().child("Users").child(mAuth.getUid());
        dataManager=((MyApp) activity.getApplication()).getDataManager();
    }


    @Override
    public void save(final String phone, final String name, final String type, final AddListener listener) {
        Map map=new HashMap<>();
        map.put("phone",phone);
        map.put("name",name);
        map.put("type",type);

        mDatabaseReference.updateChildren(map, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                  if (databaseError==null){
                      String d=type.equals(dataManager.getUser().getType())?"no":"yes";
                      dataManager.putName(name);
                      dataManager.putPhone(phone);
                      dataManager.putType(type);
                      getView().close();
                      listener.onSuccess(d);
                  }else {
                     listener.onError(databaseError.getMessage());
                  }
            }
        });
    }
}
