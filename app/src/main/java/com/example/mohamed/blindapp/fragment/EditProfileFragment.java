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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.presenter.editProfile.EditProfileViewPresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.utils.utils;
import com.example.mohamed.blindapp.view.EditProfileView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :10:19
 */

public class EditProfileFragment extends DialogFragment implements View.OnClickListener,EditProfileView{
    private Spinner mSpinner;
    private EditText nameText,phoneText;
    private Button save,close;
    private EditProfileViewPresenter presenter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private View  view;
    private DataManager dataManager;
    private static  AddListener mListener;

    public static EditProfileFragment newFragment(AddListener listener){
        mListener=listener;
        return new EditProfileFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.edit_profile,null);
        builder=new AlertDialog.Builder(getActivity()).setView(view);
        dialog=builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= LayoutInflater.from(getActivity()).inflate(R.layout.edit_profile,null);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        init();
        return view;
    }

    private void init(){
        dataManager=((MyApp) getActivity().getApplication()).getDataManager();
        presenter=new EditProfileViewPresenter(getActivity());
        presenter.attachView(this);
        mSpinner=view.findViewById(R.id.type_spinner);
        nameText=view.findViewById(R.id.edt_name);
        phoneText=view.findViewById(R.id.edt_phone);
        save=view.findViewById(R.id.save);
        close=view.findViewById(R.id.edt_close);

        nameText.setText(dataManager.getUser().getName());
        phoneText.setText(dataManager.getUser().getPhone());
        mSpinner.setSelection(dataManager.getUser().getType().equalsIgnoreCase("Blind")?0:1);
         setDataSpinner();
        close.setOnClickListener(this);
        save.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edt_close:
                close();
                break;
            case R.id.save:
                save();
                break;
        }
    }

    private void setDataSpinner(){
        List<String> list=new ArrayList<>();
        for (String s:getActivity().getResources().getStringArray(R.array.user_type)) {
            list.add(s);
        }

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_expandable_list_item_1,list);
        mSpinner.setAdapter(adapter);
    }

    private void save(){
        final String name =nameText.getText().toString();
        final String phone=phoneText.getText().toString();
        final String type=mSpinner.getSelectedItem().toString();
        if (TextUtils.isEmpty(name)){

        }else if (!utils.isValidMobile(phone)){

        }else {
            presenter.save(phone, name, type,mListener);
        }
    }

    @Override
    public void close() {
        dialog.dismiss();
    }
}
