package com.example.asus.checkinlokasi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.asus.checkinlokasi.ui.AddLocationFragment;

public class GPSReceiver extends BroadcastReceiver {


    public static final String TAG = GPSReceiver.class.getSimpleName();
    public GPSReceiver(AddLocationFragment addLocationFragment) {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if (intent.getAction().matches("android.location.PROVIDERS_CHANGED"))
        {
            // react on GPS provider change action
        }
    }
}
