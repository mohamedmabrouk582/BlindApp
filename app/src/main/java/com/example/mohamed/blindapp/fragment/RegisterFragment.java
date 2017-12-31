package com.example.mohamed.blindapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.activity.HomeActivity;
import com.example.mohamed.blindapp.activity.LoginActivity;
import com.example.mohamed.blindapp.presenter.register.RegisterViewPresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.utils.utils;
import com.example.mohamed.blindapp.view.RegisterView;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :18:16
 */

public class RegisterFragment extends Fragment implements RegisterView,View.OnClickListener{
    private RegisterViewPresenter presenter;
    private EditText nameText,phoneText,passweordText;
    private TextView login;
    private ProgressBar progressBar;
    private Spinner userType;
    private Button register;
    private View view;
    private String name,phone,pass,type;

    public static RegisterFragment newFragment(){
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.register_fragment,container,false);
        init();
        return view;
    }

    private void init(){
        presenter=new RegisterViewPresenter(getActivity());
        presenter.attachView(this);
        nameText=view.findViewById(R.id.register_name);
        phoneText=view.findViewById(R.id.phone_register);
        passweordText=view.findViewById(R.id.password_register);
        login=view.findViewById(R.id.txt_login);
        progressBar=view.findViewById(R.id.register_progressBar);
        userType=view.findViewById(R.id.user_type);
        register=view.findViewById(R.id.but_register);
        setSpinnerData();
        register.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    private void setSpinnerData(){
        List<String>  list=new ArrayList<>();
        for (String s:getActivity().getResources().getStringArray(R.array.user_type)) {
            list.add(s);
        }
         ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,list);
        userType.setAdapter(adapter);
    }

    @Override
    public void register() {
      name =nameText.getText().toString();
      phone=phoneText.getText().toString();
      pass=passweordText.getText().toString();
      type=userType.getSelectedItem().toString();


     if (TextUtils.isEmpty(name)){
         YoYo.with(Techniques.Shake).playOn(nameText);
         presenter.showSnakBar(view,"user name not be empty");
     } else if (!utils.isValidMobile(phone)){
         YoYo.with(Techniques.Shake).playOn(phoneText);
         presenter.showSnakBar(view,"your phone number  invalid ");
     }else if (TextUtils.isEmpty(pass) || pass.length()<6){
         YoYo.with(Techniques.Shake).playOn(passweordText);
         presenter.showSnakBar(view,"password must be more than 6 char");
     }else {
         Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
         showProgress();
         OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
             @Override
             public void idsAvailable(String userId, String registrationId) {
                 presenter.register(name, phone, pass, type,userId, new AddListener() {
                     @Override
                     public void onSuccess(String s) {
                         hideProgress();
                         HomeActivity.start(getActivity(),true);
                         getActivity().finish();
                     }

                     @Override
                     public void onError(String error) {
                         hideProgress();
                         Log.d("error", error + "");
                         presenter.showSnakBar(view,error);
                     }
                 });
             }
         });

     }
    }

    @Override
    public void login() {
        LoginActivity.start(getActivity());
        getActivity().finish();
    }

    @Override
    public void showProgress() {
       progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
     progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txt_login:
                 login();
                break;
            case R.id.but_register:
                register();
                break;
        }
    }
}
