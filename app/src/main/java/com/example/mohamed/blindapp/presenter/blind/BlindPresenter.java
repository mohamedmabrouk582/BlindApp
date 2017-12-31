package com.example.mohamed.blindapp.presenter.blind;

import com.example.mohamed.blindapp.oneSignal.SendOnSignalNotification;
import com.example.mohamed.blindapp.presenter.base.MainPresnter;
import com.example.mohamed.blindapp.view.BlindView;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :20:58
 */

public interface BlindPresenter<v extends BlindView> extends MainPresnter<v> {
    void help(String restKey, String appId, LatLng latLng,SendOnSignalNotification.response response);

}
