package com.example.mohamed.blindapp.presenter.chooseAction;

import com.example.mohamed.blindapp.oneSignal.SendOnSignalNotification;
import com.example.mohamed.blindapp.presenter.base.MainPresnter;
import com.example.mohamed.blindapp.view.ChooseView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :02:10
 */

public interface ChooseAction<v extends ChooseView> extends MainPresnter<v> {
    void callPhone(String phone);
    void sendNotify(String restKey, String appId, String userId, SendOnSignalNotification.response response);
    void videoCall(String phone);
}
