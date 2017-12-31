package com.example.mohamed.blindapp.presenter.register;

import com.example.mohamed.blindapp.presenter.base.MainPresnter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.view.RegisterView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :17:44
 */

public interface RegisterPresenter<v extends RegisterView> extends MainPresnter<v>{
    void register(String userName, String email, String password,String type,String userId, AddListener listener);

}
