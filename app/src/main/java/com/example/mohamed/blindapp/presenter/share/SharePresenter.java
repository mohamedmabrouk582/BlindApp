package com.example.mohamed.blindapp.presenter.share;

import com.example.mohamed.blindapp.presenter.base.MainPresnter;
import com.example.mohamed.blindapp.view.ShareView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :19:18
 */

public interface SharePresenter<v extends ShareView> extends MainPresnter<v> {
    void share(String massage,String subject);
}
