package com.example.mohamed.blindapp.presenter.login;


import com.example.mohamed.blindapp.presenter.base.MainPresnter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.view.LoginView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 18/12/2017.  time :22:57
 */

public interface LoginPresenter<v extends LoginView> extends MainPresnter<v> {
    void login(String email, String password, AddListener listener);
}
