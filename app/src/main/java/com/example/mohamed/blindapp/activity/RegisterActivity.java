package com.example.mohamed.blindapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.mohamed.blindapp.fragment.RegisterFragment;
import com.example.mohamed.blindapp.utils.SingleFragmentActivity;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 30/12/2017.  time :18:43
 */

public class RegisterActivity extends SingleFragmentActivity {
    public static void start(Context context){
        Intent intent=new Intent(context,RegisterActivity.class);
        context.startActivity(intent);
    }
    @Override
    public Fragment CreateFragment() {
        return RegisterFragment.newFragment();
    }
}
