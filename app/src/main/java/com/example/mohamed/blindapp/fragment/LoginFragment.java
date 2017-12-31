package com.example.mohamed.blindapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.activity.HomeActivity;
import com.example.mohamed.blindapp.activity.RegisterActivity;
import com.example.mohamed.blindapp.presenter.login.LoginViewPresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.utils.utils;
import com.example.mohamed.blindapp.view.LoginView;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :17:22
 */

public class LoginFragment extends Fragment implements LoginView,View.OnClickListener{
    private EditText phoneText,passText;
    private ProgressBar progressBar;
    private TextView register;
    private Button login;
    private View view;
    private LoginViewPresenter presenter;

    public static LoginFragment newFragment(){
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.login_fragment,container,false);
        init();
        return view;
    }

    private void init(){
        presenter=new LoginViewPresenter(getActivity());
        presenter.attachView(this);
        phoneText=view.findViewById(R.id.login_phone);
        passText=view.findViewById(R.id.login_password);
        register=view.findViewById(R.id.create_account);
        progressBar=view.findViewById(R.id.login_progressBar);
        login=view.findViewById(R.id.but_login);

        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void login() {
       String phone=phoneText.getText().toString();
       String pass=passText.getText().toString();

       if (!utils.isValidMobile(phone)){
           YoYo.with(Techniques.Shake).playOn(phoneText);
           presenter.showSnakBar(view,"your phone number invalid");
       }else if (TextUtils.isEmpty(pass) || pass.length()<6){
           YoYo.with(Techniques.Shake).playOn(passText);
           presenter.showSnakBar(view,"password mast be > 6 char ");
       }else {
           showProgress();
           presenter.login(phone, pass, new AddListener() {
               @Override
               public void onSuccess() {
                   hideProgress();
                   HomeActivity.start(getActivity());
                   getActivity().finish();
               }

               @Override
               public void onError(String error) {
                   hideProgress();
                   presenter.showSnakBar(view,error);

               }
           });
       }
    }

    @Override
    public void createAccount() {
        RegisterActivity.start(getActivity());
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
            case R.id.create_account:
               createAccount();
                break;
            case R.id.but_login:
                  login();
                break;
        }
    }
}
