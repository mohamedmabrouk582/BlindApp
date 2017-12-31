package com.example.mohamed.blindapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mohamed.blindapp.R;
import com.example.mohamed.blindapp.appliction.DataManager;
import com.example.mohamed.blindapp.appliction.MyApp;
import com.example.mohamed.blindapp.data.Request;
import com.example.mohamed.blindapp.presenter.helper.HelperViewPresenter;
import com.example.mohamed.blindapp.view.HelperView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by mohamed mabrouk
 * 0201152644726
 * on 31/12/2017.  time :00:09
 */

public class HelperFragment extends Fragment implements HelperView{
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private View view;
    private FirebaseRecyclerAdapter adapter;
    private Query query;
    private FirebaseRecyclerOptions options;
    private DataManager dataManager;
    private DatabaseReference mDatabaseReference;
    private HelperViewPresenter presenter;
    private FirebaseAuth mAuth;

    public static HelperFragment newFragment(){
        return new HelperFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.helper_fragment,container,false);
        init();
        iniRecyl();
        iniSwipe();
        showRequests();
        return view;
    }

    private void init(){
        presenter=new HelperViewPresenter();
        presenter.attachView(this);
        mAuth=MyApp.getAuth();
        mDatabaseReference= MyApp.getDatabaseReference().child("Notifications").child(mAuth.getUid());
        query=mDatabaseReference;
        options=new FirebaseRecyclerOptions.Builder<Request>().setQuery(query,Request.class).build();


    }

    private void iniRecyl(){
        mRecyclerView=view.findViewById(R.id.notifications_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void iniSwipe(){
        mSwipeRefreshLayout=view.findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                  showRequests();
                adapter.startListening();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void showProgress() {
        if (!mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideProgress() {
        if (mSwipeRefreshLayout.isRefreshing()){
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void requestClickedMessage(Request request) {

    }

    @Override
    public void showRequests() {
        showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideProgress();
            }
        },1000l);


       adapter=new FirebaseRecyclerAdapter<Request,Holder>(options) {
           @Override
           public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
               View view=LayoutInflater.from(getActivity()).inflate(R.layout.request_help,parent,false);
               return new Holder(view);
           }

           @Override
           protected void onBindViewHolder(@NonNull Holder holder, final int position, @NonNull final Request model) {
             holder.bind(model);
             holder.itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                     ChooseActionFragment fragment=ChooseActionFragment.newFragment(model);
                     fragment.show(fragmentManager,"");
                 }
             });
             holder.delete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     notifyItemRemoved(position);
                     mDatabaseReference.child(model.getId()).removeValue();
                 }
             });
           }
       };


       mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    class Holder extends RecyclerView.ViewHolder{
        public ImageView delete;
        public TextView name,phone,place,location;

        public Holder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.blind_name);
            phone=itemView.findViewById(R.id.blind_phone);
            place=itemView.findViewById(R.id.blind_place);
            location=itemView.findViewById(R.id.blind_location);
            delete=itemView.findViewById(R.id.delete);



        }

        public void bind(Request request){
            name.setText(request.getName());
            place.setText(request.getBody());
            phone.setText(request.getPhone());
            location.setText(request.getLocation());
        }

    }
}
