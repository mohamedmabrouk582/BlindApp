package com.example.mohamed.blindapp.presenter.home;

import android.net.Uri;

import com.example.mohamed.blindapp.data.User;
import com.example.mohamed.blindapp.presenter.base.MainPresnter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.view.HomeView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :16:24
 */

public interface HomePresenter<v extends HomeView> extends MainPresnter<v> {
    void logout();
    void editProfile( AddListener listener);
    void editImg(Uri uri,AddListener listener);
}
