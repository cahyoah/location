package com.example.asus.checkinlokasi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class LocationReceiver extends BroadcastReceiver {

    public static final String TAG = LocationReceiver.class.getSimpleName();

    private final PeriodicCheckLocation mListener;

    public LocationReceiver(PeriodicCheckLocation listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String location = intent.getStringExtra("location");
        mListener.handleFromReceiver(location);
    }

    public interface PeriodicCheckLocation {

        void handleFromReceiver(String location);
    }
}
