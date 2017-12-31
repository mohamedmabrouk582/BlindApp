package com.example.mohamed.blindapp.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.oneSignal.SendOnSignalNotification;
import com.example.mohamed.blindapp.presenter.blind.BlindViewPresenter;
import com.example.mohamed.blindapp.view.BlindView;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :21:55
 */

public class BlindFragment extends Fragment implements View.OnClickListener,BlindView,LocationListener,SendOnSignalNotification.response {
    private Button helpButton;
    private BlindViewPresenter presenter;
    private String mLocation;
    private TextToSpeech mTextToSpeech;

    public static BlindFragment blindFragment(){
        return new BlindFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.blind_fragment,container,false);
        helpButton=view.findViewById(R.id.help);
        presenter=new BlindViewPresenter(getActivity());
        presenter.attachView(this);
        helpButton.setOnClickListener(this);
        return view;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onClick(View view) {
        mTextToSpeech=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS) {
                    mTextToSpeech.setLanguage(Locale.US);
                    mTextToSpeech.speak("Hello .  your help . Request Processing . please Wait", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        LocationManager locationManager= (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
         locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER,this, Looper.getMainLooper());
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getActivity(), location.getLatitude()+" : "+location.getLongitude(), Toast.LENGTH_SHORT).show();
        LatLng latLng=new LatLng(location.getLatitude(),location.getLongitude());
        presenter.help("MGUwMTBhZmQtYWI0Yy00Y2M1LWI2NmItODBhOTQ4ZDdhMDQ3","5c87b77c-3c94-4d43-9b01-7cc3030e79e6",latLng,this);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onResponse(final String response) {
        Log.d("response", response + "");
        mTextToSpeech=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS) {
                    mTextToSpeech.setLanguage(Locale.US);
                    mTextToSpeech.speak("congratulations . Your Notification  . send Successfully  ", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(final String error) {
        Log.d("error", error + "");
        mTextToSpeech=new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i == TextToSpeech.SUCCESS) {
                    mTextToSpeech.setLanguage(Locale.US);
                    mTextToSpeech.speak(error, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPause() {
        if (mTextToSpeech!=null){
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onPause();
    }
}
