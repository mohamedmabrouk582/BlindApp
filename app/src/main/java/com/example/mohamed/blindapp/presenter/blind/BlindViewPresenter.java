package com.example.mohamed.blindapp.presenter.blind;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.oneSignal.SendOnSignalNotification;
import com.example.mohamed.blindapp.oneSignal.model.ButtonData;
import com.example.mohamed.blindapp.oneSignal.model.Content;
import com.example.mohamed.blindapp.oneSignal.model.Data;
import com.example.mohamed.blindapp.oneSignal.model.Heading;
import com.example.mohamed.blindapp.presenter.base.BasePresenter;
import com.example.mohamed.blindapp.view.BlindView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :20:58
 */

public class BlindViewPresenter<v extends BlindView> extends BasePresenter<v> implements BlindPresenter<v> {
    private DataManager dataManager;
    private Activity activity;

    public BlindViewPresenter(Activity activity){
        this.activity=activity;
        dataManager=((MyApp) activity.getApplication()).getDataManager();
    }


    @Override
    public void help(String restKey, String appId, LatLng latLng,SendOnSignalNotification.response response) {
        new SendOnSignalNotification.SendOnSignalNotificationBuilder(restKey,appId)
                .setAddinalData(new Data("userid",dataManager.getUser().getUserid()),new Data("name",dataManager.getUser().getName()),new Data("location",String.valueOf(latLng)),new Data("phone",dataManager.getUser().getPhone()))
                .setHeadings(new Heading("en","HELP ME "))
                .setContents(new Content("en",address(latLng)))
                 .setSegemts("Active Users")
                .setButtons(new ButtonData("1","help"),new ButtonData("2","Cancel"))
                .build(response);
    }

    private String address(LatLng latLng){
        String addres = null;
        try {


            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(activity, Locale.getDefault());

            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            addres=address;
        }catch (Exception e){}
        return addres;
    }
}
