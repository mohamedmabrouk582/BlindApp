package com.example.mohamed.blindapp.presenter.chooseAction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.oneSignal.SendOnSignalNotification;
import com.example.mohamed.blindapp.oneSignal.model.Content;
import com.example.mohamed.blindapp.oneSignal.model.Data;
import com.example.mohamed.blindapp.oneSignal.model.Heading;
import com.example.mohamed.blindapp.presenter.base.BasePresenter;
import com.example.mohamed.blindapp.view.ChooseView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :02:12
 */

public class ChooseActionViewPresenter<v extends ChooseView> extends BasePresenter<v> implements ChooseAction<v> {
    private Activity activity;
    private DataManager dataManager;

    public ChooseActionViewPresenter(Activity activity){
        this.activity=activity;
        dataManager=((MyApp) activity.getApplication()).getDataManager();
    }

    @Override
    public void callPhone(String phone) {
        activity.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone)));

    }

    @Override
    public void sendNotify(String restKey, String appId, String userId, SendOnSignalNotification.response response) {
       new SendOnSignalNotification.SendOnSignalNotificationBuilder(restKey,appId)
               .setPlyerId(userId)
               .setHeadings(new Heading("en","i am helper"))
               .setContents(new Content("en","i will help  you"))
               .setAddinalData(new Data("location",dataManager.getUser().getPhone()+""))
               .build(response);
    }

    @Override
    public void videoCall(String phone) {
//        PhoneCallListener phoneListener = new PhoneCallListener(activity);
//        TelephonyManager telephonyManager = (TelephonyManager)
//                activity.getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
//        Intent intent = new Intent("com.android.phone.videocall",Uri.parse("tel:"+phone));
//        intent.putExtra("videocall", true);
//        activity.startActivity(intent);
        Toast.makeText(activity, "not Developed yet   :)", Toast.LENGTH_SHORT).show();
    }
}
