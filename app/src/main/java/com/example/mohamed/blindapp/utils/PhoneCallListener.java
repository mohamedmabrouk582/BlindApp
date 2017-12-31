package com.example.mohamed.blindapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :09:55
 */

public class PhoneCallListener extends PhoneStateListener {

    private boolean isPhoneCalling = false;
    private Context activity;
    String LOG_TAG = "LOGGING 123";

    public PhoneCallListener(Context activity){
        this.activity=activity;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {

        if (TelephonyManager.CALL_STATE_RINGING == state) {
            // phone ringing
            Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
        }

        if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
            // active
            Log.i(LOG_TAG, "OFFHOOK");

            isPhoneCalling = true;
        }

        if (TelephonyManager.CALL_STATE_IDLE == state) {
            // run when class initial and phone call ended,
            // need detect flag from CALL_STATE_OFFHOOK
            Log.i(LOG_TAG, "IDLE");

            if (isPhoneCalling) {

                Log.i(LOG_TAG, "restart app");

                // restart app
                Intent i = activity.getPackageManager()
                        .getLaunchIntentForPackage(
                                activity.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(i);

                isPhoneCalling = false;
            }

        }
    }

}
