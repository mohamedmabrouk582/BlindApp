package com.example.mohamed.blindapp.utils;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;
import com.onesignal.OneSignal;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 06/12/2017.  time :16:36
 */

public class NotificationExtenderExample extends NotificationExtenderService {
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    Map<String,String> map=new HashMap<>();
    private boolean flage;

    @Override
    protected boolean onNotificationProcessing(OSNotificationReceivedResult notification) {

        try {

            mAuth= MyApp.getAuth();
            mDatabaseReference=MyApp.getDatabaseReference().child("Notifications").child(mAuth.getUid());
            if (!notification.payload.additionalData.getString("location").contains("lat/lng")){
                // call    ***********
                Log.d("rrrr", "rrrrrrrrrrr" +notification.payload.additionalData.getString("location") );
           //    Result.getInstance().setmsg(notification.payload.additionalData.getString("location"));
                 startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+notification.payload.additionalData.getString("location"))));
                 flage=true;
            }else {

                flage=false;
                map.put("id", notification.payload.notificationID);
                map.put("body", notification.payload.body);
                map.put("name", notification.payload.additionalData.getString("name"));
                map.put("phone", notification.payload.additionalData.getString("phone"));
                map.put("location", notification.payload.additionalData.getString("location"));
                map.put("userid", notification.payload.additionalData.getString("userid"));

                mDatabaseReference.child(notification.payload.notificationID).setValue(map);

                Log.d("asas", notification.payload.toJSONObject().toString(4));
                // Result.getInstance().setmsg(notification.payload.additionalData.toString());

                Log.d("show", notification.payload.toJSONObject().toString(4));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        OverrideSettings overrideSettings=new OverrideSettings();
//        overrideSettings.extender=new NotificationCompat.Extender() {
//            @Override
//            public NotificationCompat.Builder extend(NotificationCompat.Builder builder) {
//                Bitmap icon= BitmapFactory.decodeResource(MyApp.getContext().getResources(),
//                        R.drawable.common_full_open_on_phone);
//                builder.setLargeIcon(icon);
//                return builder.setColor(new BigInteger("FF0000FF", 16).intValue());
//            }
//        };
//
//        OSNotificationDisplayedResult osNotificationDisplayedResult=displayNotification(overrideSettings);
        return flage;
    }
}
