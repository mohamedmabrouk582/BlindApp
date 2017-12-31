package com.example.mohamed.blindapp.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import com.example.mohamed.blindapp.presenter.home.HomeViewPresenter;
import com.example.mohamed.blindapp.utils.AddListener;
import com.example.mohamed.blindapp.utils.Result;
import com.example.mohamed.blindapp.view.HomeView;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , HomeView{
    private static final String USER = "USERS";
    private static final int PERMISSION = 0;
    private CircleImageView img;
    private TextView name,phone;
    private User mUser;
    private HomeViewPresenter presenter;
    private String[] permissions={Manifest.permission.CALL_PHONE,Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS};
    private DataManager dataManager;
    public static void start( Context context){
        Intent intent=new Intent(context,HomeActivity.class);
        context.startActivity(intent);
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
                public void onSuccess() {
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void checkUser(String type) {
        Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
        if (type.equals("Blind")){
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
                    requestPermissions(new String[]{permissions[i]}, PERMISSION);
                }
            }
        }catch (Exception e){}
    }

    @Override
    public void setFragment(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.Fragment_Container,fragment).commit();
    }


}
