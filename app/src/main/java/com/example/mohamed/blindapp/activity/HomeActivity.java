package com.example.mohamed.blindapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.data.User;
import com.example.mohamed.blindapp.fragment.BlindFragment;
import com.example.mohamed.blindapp.fragment.EditProfileFragment;
import com.example.mohamed.blindapp.fragment.HelperFragment;
import com.example.mohamed.blindapp.fragment.ShareFragment;
import com.example.mohamed.blindapp.presenter.home.HomeViewPresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.utils.Result;
import com.example.mohamed.blindapp.view.HomeView;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , HomeView{
    private static final String USER = "USERS";
    private static final int PERMISSION = 0;
    private static final String LOGIN = "login";
    private CircleImageView img;
    private TextView name,phone;
    private User mUser;
    private TextToSpeech mTextToSpeech;

    private HomeViewPresenter presenter;
    private String[] permissions={Manifest.permission.CALL_PHONE,Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};
    private DataManager dataManager;
    public static void start(Activity context, boolean isLogin){
            if (isLogin){
                Intent i = context.getPackageManager()
                        .getLaunchIntentForPackage(
                                context.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(i);
                System.exit(0);

            }else {
                Intent intent = new Intent(context, HomeActivity.class);
                context.startActivity(intent);
            }



    }

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        dataManager=((MyApp) getApplication()).getDataManager();
        mUser=dataManager.getUser();
        Log.d("user", mUser.toString()+ "");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermissions();
        }
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        presenter=new HomeViewPresenter(this);
        presenter.attachView(this);
        init(navigationView.getHeaderView(0));
        setData(mUser);




    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void init(View view){
        img=view.findViewById(R.id.img);
        name=view.findViewById(R.id.name);
        phone=view.findViewById(R.id.phone);
    }

    private void setData(User data){
            checkUser(data.getType());
            name.setText(data.getName());
            phone.setText(String.valueOf(data.getPhone()));

    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_edit) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            EditProfileFragment fragment=EditProfileFragment.newFragment(new AddListener() {
                @Override
                public void onSuccess(String s) {
                    mUser=dataManager.getUser();
                    setData(mUser);
                }

                @Override
                public void onError(String error) {

                }
            });
            fragment.show(fragmentManager,"");
            // Handle the camera action
        } else if (id == R.id.nav_logout) {
             presenter.logout();
        }else if (id==R.id.nav_invite){
            FragmentManager fragmentManager=getSupportFragmentManager();
            ShareFragment fragment=new ShareFragment();
            fragment.show(fragmentManager,"");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void checkUser(String type) {
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        if (type.equals("Blind")){
            mTextToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if(i == TextToSpeech.SUCCESS) {
                       mTextToSpeech.setLanguage(Locale.US);
                        mTextToSpeech.speak("Hello . i hope you . are well . click to . screen  center . to send . notification", TextToSpeech.QUEUE_FLUSH, null);

                    }
                }
            });



            setFragment(BlindFragment.blindFragment());
        }else {
           setFragment(HelperFragment.newFragment());
        }
    }

    private boolean hasPermission(String permission){
        return ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void  checkPermissions(){
        try {
            for (int i = 0; i <permissions.length ; i++) {
                if (hasPermission(permissions[i])) {
//                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,permissions[i])) {
//
//                    } else {
//                        requestPermissions(new String[]{permissions[i]}, PERMISSION);
//                    }
                    requestPermissions(permissions, PERMISSION);
                }
            }
        }catch (Exception e){

        }
    }

    @Override
    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Fragment_Container,fragment).commit();
    }

    @Override
    protected void onPause() {
        if (mTextToSpeech!=null){
            mTextToSpeech.stop();
            mTextToSpeech.shutdown();
        }
        super.onPause();
    }
}
