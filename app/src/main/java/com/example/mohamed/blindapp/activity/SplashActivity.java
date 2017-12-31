package com.example.mohamed.blindapp.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.data.User;
import com.example.mohamed.blindapp.presenter.splash.SplashViewPresenter;
import com.example.mohamed.blindapp.view.SplashView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 27/12/2017.  time :22:00
 */

public class SplashActivity extends AppCompatActivity implements SplashView {
      private FirebaseAuth mAuth;
      private SplashViewPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        mAuth= MyApp.getAuth();
        presenter=new SplashViewPresenter(this);
        presenter.attachView(this);

    }



    @Override
    protected void onStart() {
        super.onStart();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser user=mAuth.getCurrentUser();
                if (user!=null){
                   presenter.HomeActivity();
                }else {
                   presenter.loginActivity();
                }
            }
        },1000);

    }
}
