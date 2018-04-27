package com.example.asus.checkinlokasi.ui;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.checkinlokasi.R;
import com.example.asus.checkinlokasi.adapter.ListLocationAdapter;
import com.example.asus.checkinlokasi.data.network.model.Location;
import com.example.asus.checkinlokasi.presenter.ListLocationPresenter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListLocationFragment extends Fragment implements ListLocationView, View.OnClickListener {

    private ListLocationAdapter listLocationAdapter;
    private ListLocationPresenter listLocationPresenter;
    private RecyclerView rvLocation;
    private ProgressBar pbLoading;
    private TextView tvError;
    private SwipeRefreshLayout srlLocation;
    private FloatingActionButton fbAddLocation;
    private AlertDialog alert;
    private AlertDialog.Builder builder;

    public ListLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_location, container, false);
        rvLocation = view.findViewById(R.id.rv_location);
        pbLoading = view.findViewById(R.id.pb_loading);
        tvError = view.findViewById(R.id.tv_error);
        fbAddLocation = view.findViewById(R.id.fb_add_location);
        srlLocation = view.findViewById(R.id.srl_location);
        srlLocation.setOnRefreshListener(() -> {
            tvError.setText("");
            pbLoading.setVisibility(View.VISIBLE);
            rvLocation.setVisibility(View.GONE);
            listLocationPresenter.showAllLocation();
            srlLocation.setRefreshing(false);
        });
        initView();
        initPresenter();
        return view;
    }


    private void initPresenter() {
        listLocationPresenter = new ListLocationPresenter(this);
        listLocationPresenter.showAllLocation();
    }

    private void initView() {
        listLocationAdapter = new ListLocationAdapter(getActivity());
        rvLocation.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLocation.setAdapter(listLocationAdapter);
        fbAddLocation.setOnClickListener(this);
    }

    @Override
    public void onSuccessShowAllLocation(List<Location> locationList) {
        rvLocation.setVisibility(View.GONE);
        tvError.setText("Tidak ada reminder");
        tvError.setVisibility(View.VISIBLE);
        if(locationList.size()==0 || locationList.isEmpty()){
            rvLocation.setVisibility(View.GONE);
            pbLoading.setVisibility(View.GONE);
            tvError.setText("Tidak ada reminder");
            tvError.setVisibility(View.VISIBLE);
        }else{
            tvError.setVisibility(View.GONE);
            pbLoading.setVisibility(View.GONE);
            rvLocation.setVisibility(View.VISIBLE);
            listLocationAdapter.setData(locationList);
            rvLocation.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailedShowAllLocation(String data_not_found) {
        rvLocation.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(data_not_found);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fb_add_location){
            getFragmentManager().beginTransaction().
                    setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                    add(R.id.frame_container,
                            new AddLocationFragment(),
                            AddLocationFragment.class.getSimpleName())
                    .addToBackStack( AddLocationFragment.class.getSimpleName())
                    .commit();
        }
    }
}
