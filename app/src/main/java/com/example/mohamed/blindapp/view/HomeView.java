package com.example.mohamed.blindapp.view;

import android.support.v4.app.Fragment;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :16:21
 */

public interface HomeView extends  MainView{
    void checkUser(String type);
    void setFragment(Fragment fragment);
}
