package com.example.asus.checkinlokasi.ui;

import android.location.Location;

public interface LocationView {
    void onSuccessGetLocation(Location addresses);

    void onDisabledGPS(String please_enable_gps_and_internet);
}
