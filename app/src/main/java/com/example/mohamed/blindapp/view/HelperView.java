package com.example.mohamed.blindapp.view;

import com.example.mohamed.blindapp.data.Request;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :00:03
 */

public interface HelperView extends MainView {
    void showProgress();
    void hideProgress();
    void requestClickedMessage(Request request);
    void showRequests();
}
