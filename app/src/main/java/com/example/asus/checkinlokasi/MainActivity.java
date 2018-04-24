package com.example.asus.checkinlokasi;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements LocationView, View.OnClickListener {

    private EditText etLocationName, etNote, etKontributor;
    private Button btnSave, btnSetLocation;
    private TextView tvLongitudeLatitude;
    private LocationPresenter locationPresenter;
    private AlertDialog alert;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etLocationName = findViewById(R.id.et_location_name);
        etNote = findViewById(R.id.et_note);
        etKontributor = findViewById(R.id.et_kontributor);
        btnSave = findViewById(R.id.btn_save);
        btnSetLocation = findViewById(R.id.btn_set_location);
        tvLongitudeLatitude = findViewById(R.id.tv_longitude_latitude);
        initView();
        initPresenter();
    }

    private void initView() {
        btnSetLocation.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

    private void initPresenter() {
        locationPresenter = new LocationPresenter(MainActivity.this, MainActivity.this);
        locationPresenter.getLocation();
    }

    @Override
    public void onSuccessGetLocation(Location addresses) {
        if(locationPresenter == null){
            locationPresenter = new LocationPresenter(this, this);
            locationPresenter.getLocation();
        }
        tvLongitudeLatitude.setText(Double.toString(addresses.getLongitude()) +", "+ Double.toString(addresses.getLatitude()) );
    }

    @Override
    public void onDisabledGPS(String please_enable_gps_and_internet) {
        builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Terjadi kesalahan. Apakah anda ingin mengulang?");
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
//                if(ShowAlert.dialog != null && ShowAlert.dialog.isShowing()){
//                    ShowAlert.closeProgresDialog();
//                }
//                ShowAlert.showProgresDialog(TesMinatActivity.this);
//                tesMinatPresenter.showTesMinatQuestion();
            }
        });

        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
               // TesMinatActivity.super.onBackPressed();
            }
        });

        alert = builder.create();
        alert.show();
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

            }
        }
    }
}
