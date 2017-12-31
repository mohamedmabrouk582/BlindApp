package com.example.mohamed.blindapp.presenter.share;

import android.app.Activity;
import android.content.Intent;

import com.example.mohamed.blindapp.presenter.base.BasePresenter;
import com.example.mohamed.blindapp.view.ShareView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :19:19
 */

public class ShareViewPresenter<v extends ShareView> extends BasePresenter<v> implements SharePresenter<v> {
    private Activity activity;

    public ShareViewPresenter (Activity activity){
        this.activity=activity;
    }

    @Override
    public void share(String massage,String subject) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, massage);
        activity.startActivity(Intent.createChooser(sharingIntent,"Choose ...."));
        getView().close();
    }
}
