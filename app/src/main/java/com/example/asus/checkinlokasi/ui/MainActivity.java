package com.example.asus.checkinlokasi.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.asus.checkinlokasi.receiver.LocationReceiver;
import com.example.asus.checkinlokasi.R;
import com.example.asus.checkinlokasi.presenter.LocationPresenter;
import com.example.asus.checkinlokasi.service.LocationService;
import com.example.asus.checkinlokasi.util.ShowAlert;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction().
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).
                replace(R.id.frame_container,
                        new ListLocationFragment(),
                        ListLocationFragment.class.getSimpleName()).commit();
        setContentView(R.layout.activity_main);

    }


    @Override
    public void onBackPressed() {
        getSupportActionBar().setTitle("Daftar Lokasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        super.onBackPressed();
    }
}
