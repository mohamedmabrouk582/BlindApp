package com.example.mohamed.blindapp.presenter.home;

import android.app.Activity;
import android.net.Uri;

import com.example.mohamed.blindapp.activity.LoginActivity;
import com.example.mohamed.blindapp.activity.SplashActivity;
import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.presenter.base.BasePresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.view.HomeView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :16:27
 */

public class HomeViewPresenter<v extends HomeView> extends BasePresenter<v> implements HomePresenter<v> {
    private Activity activity;
    private DataManager dataManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseReference;

    public HomeViewPresenter(Activity activity){
        this.activity=activity;
        dataManager=((MyApp) activity.getApplication()).getDataManager();
        mAuth=MyApp.getAuth();
        mDatabaseReference=MyApp.getDatabaseReference().child("Users").child(mAuth.getUid());
    }

    @Override
    public void logout() {
        dataManager.clear();
        mAuth.signOut();
        LoginActivity.start(activity);
        activity.finish();
    }

    @Override
    public void editProfile(AddListener listener) {

    }

    @Override
    public void editImg(Uri uri, AddListener listener) {

    }
}
