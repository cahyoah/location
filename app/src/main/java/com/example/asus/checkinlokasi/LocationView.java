package com.example.asus.checkinlokasi;

import android.location.Location;

interface LocationView {
    void onSuccessGetLocation(Location addresses);

    void onDisabledGPS(String please_enable_gps_and_internet);
}
