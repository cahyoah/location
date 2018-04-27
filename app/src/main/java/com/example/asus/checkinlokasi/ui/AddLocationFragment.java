package com.example.asus.checkinlokasi.ui;


import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.checkinlokasi.R;
import com.example.asus.checkinlokasi.presenter.AddLocationPresenter;
import com.example.asus.checkinlokasi.presenter.LocationPresenter;
import com.example.asus.checkinlokasi.receiver.LocationReceiver;
import com.example.asus.checkinlokasi.service.LocationService;
import com.example.asus.checkinlokasi.util.ShowAlert;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddLocationFragment extends Fragment implements LocationView, View.OnClickListener, LocationReceiver.PeriodicCheckLocation, AddLocationView {

    private EditText etLocationName, etNote, etKontributor;
    private Button btnSave, btnSetLocation;
    private TextView tvLongitudeLatitude;
    private LocationPresenter locationPresenter;
    private Intent intent;
    private AddLocationPresenter addLocationPresenter;
    private String longitude, latitude;

    private LocationReceiver mBroadcast;
    public AddLocationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_location, container, false);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Simpan Lokasi");
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        setHasOptionsMenu(true);
        etLocationName = view.findViewById(R.id.et_location_name);
        etNote = view.findViewById(R.id.et_note);
        etKontributor = view.findViewById(R.id.et_kontributor);
        btnSave = view.findViewById(R.id.btn_save);
        btnSetLocation = view.findViewById(R.id.btn_set_location);
        tvLongitudeLatitude = view.findViewById(R.id.tv_longitude_latitude);
        initView();
        initPresenter();
        initService();
        registerReceiver();
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver();
        if (intent != null){
            getActivity().stopService(intent);
        }

    }

    public void registerReceiver() {
        mBroadcast = new LocationReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(LocationReceiver.TAG);
        getActivity().registerReceiver(mBroadcast, filter);

    }

    private void unregisterReceiver() {
        try {
            if (mBroadcast != null) {
                getActivity().unregisterReceiver(mBroadcast);
            }
        } catch (Exception e) {
            Log.i("", "broadcastReceiver is already unregistered");
            mBroadcast = null;
        }

    }

    private void initService() {

        intent = new Intent(getActivity(), LocationService.class);
        getActivity().startService(intent);
    }

    private void initView() {
        btnSetLocation.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

    private void initPresenter() {
        locationPresenter = new LocationPresenter(getActivity(), this);
        locationPresenter.getLocation();
        addLocationPresenter = new AddLocationPresenter(this);
    }

    @Override
    public void onSuccessGetLocation(Location addresses) {
        if(addresses != null){
            if(locationPresenter == null){
                locationPresenter = new LocationPresenter(getActivity(), this);
                locationPresenter.getLocation();
            };
            latitude = Double.toString(addresses.getLatitude());
            longitude = Double.toString(addresses.getLongitude());
            tvLongitudeLatitude.setText(Double.toString(addresses.getLongitude()) +", "+ Double.toString(addresses.getLatitude()) );
        }

    }

    @Override
    public void onDisabledGPS(String please_enable_gps_and_internet) {
       tvLongitudeLatitude.setText(please_enable_gps_and_internet +" Tidak Aktif");
       latitude = null;
       longitude = null;
    }

    @Override
    public void onProviderEnabled() {
        locationPresenter.getLocation();
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_set_location){
            locationPresenter.getLocation();
        }
        if(v.getId() == R.id.btn_save){
            String locationName = etLocationName.getText().toString().trim();
            String note = etNote.getText().toString().trim();
            String contributor = etKontributor.getText().toString().trim();
            if(locationName.isEmpty()){
                etLocationName.setError(getString(R.string.text_cannot_empty));
                etLocationName.requestFocus();
            }else if(note.isEmpty()){
                etNote.setError(getString(R.string.text_cannot_empty));
                etNote.requestFocus();
            }else if(contributor.isEmpty()){
                etKontributor.setError(getString(R.string.text_cannot_empty));
                etKontributor.requestFocus();
            }else {
                if(ShowAlert.dialog != null){
                    ShowAlert.closeProgresDialog();
                }
                ShowAlert.showProgresDialog(getActivity());
                addLocationPresenter.postLocation(locationName, note, contributor, longitude, latitude, "simpan" );
            }
        }
    }

    @Override
    public void handleFromReceiver(String location) {
        if(location != null){
            String[] locationParse = location.split(", ");
            latitude = locationParse[1];
            longitude = locationParse[0];
            tvLongitudeLatitude.setText(location);
        }

    }

    @Override
    public void onSuccessPostLocation(String g) {
        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(getActivity(), g);
    }

    @Override
    public void onFailedPostLocation(String t) {

        ShowAlert.closeProgresDialog();
        ShowAlert.showToast(getActivity(), t);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            ((MainActivity)getActivity()).getSupportActionBar().setTitle("Daftar Lokasi");
            ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }
}
