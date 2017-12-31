package com.example.mohamed.blindapp.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.presenter.share.ShareViewPresenter;
import com.example.mohamed.blindapp.view.ShareView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :19:23
 */

public class ShareFragment extends DialogFragment implements ShareView,View.OnClickListener {
    private EditText subjectText,massageText;
    private Button share,close;
    private ShareViewPresenter presenter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View view;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.share_view,null);
        builder=new AlertDialog.Builder(getActivity()).setView(view);
        dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.share_view,null);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
        return view;
    }

    private void init() {
        presenter=new ShareViewPresenter(getActivity());
        presenter.attachView(this);
        subjectText=view.findViewById(R.id.edt_subject);
        massageText=view.findViewById(R.id.edt_massage);
        share=view.findViewById(R.id.but_share);
        close=view.findViewById(R.id.edt_share_close);

        share.setOnClickListener(this);
        close.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edt_share_close:
                close();
                break;
            case R.id.but_share:
               String subject=subjectText.getText().toString();
               String massage=massageText.getText().toString();

               if (TextUtils.isEmpty(subject)){
                   YoYo.with(Techniques.Shake).playOn(subjectText);
                   presenter.showSnakBar(view,"Subject not be empty  ");
               }else if (TextUtils.isEmpty(massage)){
                   YoYo.with(Techniques.Shake).playOn(massageText);
                   presenter.showSnakBar(view,"Massage not be empty");
               }else {
                   presenter.share(massage,subject);
               }
                break;
        }
    }

    @Override
    public void close() {
         dialog.dismiss();
    }
}
