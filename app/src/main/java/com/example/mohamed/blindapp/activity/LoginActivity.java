package com.example.mohamed.blindapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.blindapp.fragment.LoginFragment;
import com.example.mohamed.blindapp.utils.SingleFragmentActivity;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :18:43
 */

public class LoginActivity extends SingleFragmentActivity {
    public static void start(Context context){
        Intent intent=new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
    @Override
    public Fragment CreateFragment() {
        return LoginFragment.newFragment();
    }
}
