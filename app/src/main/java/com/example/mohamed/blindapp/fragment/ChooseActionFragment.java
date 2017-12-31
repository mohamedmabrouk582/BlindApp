package com.example.mohamed.blindapp.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.data.Request;
import com.example.mohamed.blindapp.oneSignal.SendOnSignalNotification;
import com.example.mohamed.blindapp.presenter.chooseAction.ChooseActionViewPresenter;
import com.example.mohamed.blindapp.view.ChooseView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :02:30
 */

public class ChooseActionFragment extends DialogFragment implements View.OnClickListener,ChooseView{
    private static final String REQUEST = "REQUEST";
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View view;
    private Button phoneCall,videoCall,sendNotify,close;
    private ChooseActionViewPresenter presenter;
    private Request mRequest;

    public static ChooseActionFragment newFragment(Request request){
        Bundle bundle=new Bundle();
        bundle.putParcelable(REQUEST,request);
        ChooseActionFragment fragment=new ChooseActionFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
     view=LayoutInflater.from(getActivity()).inflate(R.layout.chosse_action,null);

     builder=new AlertDialog.Builder(getActivity()).setView(view);

     dialog=builder.create();
     dialog.show();
     dialog.setCanceledOnTouchOutside(false);
     return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.chosse_action,null);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
        return view;
    }

    private void init(){
        mRequest=getArguments().getParcelable(REQUEST);
        presenter=new ChooseActionViewPresenter(getActivity());
        presenter.attachView(this);
        phoneCall=view.findViewById(R.id.phone_call);
        videoCall=view.findViewById(R.id.video_call);
        sendNotify=view.findViewById(R.id.send_notify);
        close=view.findViewById(R.id.close);

        close.setOnClickListener(this);
        phoneCall.setOnClickListener(this);
        videoCall.setOnClickListener(this);
        sendNotify.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.phone_call:
            presenter.callPhone(mRequest.getPhone());
                break;
            case R.id.video_call:
                   presenter.videoCall(mRequest.getPhone());
                break;
            case R.id.send_notify:
                presenter.sendNotify("MGUwMTBhZmQtYWI0Yy00Y2M1LWI2NmItODBhOTQ4ZDdhMDQ3", "5c87b77c-3c94-4d43-9b01-7cc3030e79e6"
                        , mRequest.getUserid(), new SendOnSignalNotification.response() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("response", response + "");
                            }

                            @Override
                            public void onError(String error) {
                                Log.d("response", error + "");

                            }
                        });
                break;
            case R.id.close:
                close();
                break;
        }
    }

    @Override
    public void close() {
        dialog.dismiss();
    }
}
