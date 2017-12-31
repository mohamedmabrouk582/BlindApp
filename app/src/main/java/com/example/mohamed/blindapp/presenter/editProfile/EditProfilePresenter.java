package com.example.mohamed.blindapp.presenter.editProfile;

import com.example.mohamed.blindapp.presenter.base.MainPresnter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.view.EditProfileView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 18/12/2017.  time :21:49
 */

public interface EditProfilePresenter<v extends EditProfileView> extends MainPresnter<v> {

    void save(String phone, String name,String type, AddListener listener);
}
