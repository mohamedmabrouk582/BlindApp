package com.example.mohamed.blindapp.presenter.splash;

import android.app.Activity;

import com.example.mohamed.blindapp.activity.HomeActivity;
import com.example.mohamed.blindapp.activity.LoginActivity;
import com.example.mohamed.blindapp.data.User;
import com.example.mohamed.blindapp.presenter.base.BasePresenter;
import com.example.mohamed.blindapp.view.SplashView;


/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 18/12/2017.  time :18:07
 */

public class SplashViewPresenter<v extends SplashView> extends BasePresenter<v> implements SplashPrenter<v> {

    private Activity activity;

    public SplashViewPresenter(Activity activity){
        this.activity=activity;
    }


    @Override
    public void HomeActivity() {
        HomeActivity.start(activity,false);
        activity.finish();
    }

    @Override
    public void loginActivity() {
        LoginActivity.start(activity);
        activity.finish();
    }
}
